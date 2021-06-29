package com.yibi.evidence.chain.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yibi.evidence.chain.enums.ChainStatusEnum;
import com.yibi.evidence.chain.enums.HashCalEnum;
import com.yibi.evidence.chain.persist.entity.EviAttachEntity;
import com.yibi.evidence.chain.persist.entity.EviDataMainEntity;
import com.yibi.evidence.chain.persist.mapper.IEviDataMainMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.*;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.vo.req.DataFileReq;
import com.yibi.evidence.chain.vo.req.DataItemReq;
import com.yibi.evidence.chain.vo.req.DataReq;
import com.yibi.evidence.chain.vo.req.DataSaveReq;
import com.yibi.evidence.chain.vo.resp.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

/**
 * <p>
 * 存证主数据表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviDataMainServiceImpl extends ServiceImpl<IEviDataMainMapper, EviDataMainEntity> implements EviDataMainService {
    @Resource
    private EviProductService productService;
    @Resource
    private EviStepService stepService;
    @Resource
    private EviAttachService attachService;
    @Resource
    private EviFieldService fieldService;
    @Resource
    private EviContractService contractService;
    @Resource
    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public EviResponse dataList(DataReq dataReq) {
        LambdaQueryWrapper<EviDataMainEntity> wrapper = new LambdaQueryWrapper<>();
        if (null != dataReq.getProductId()) {
            wrapper.eq(EviDataMainEntity::getProductId, dataReq.getProductId());
        }
        if (null != dataReq.getStepId()) {
            wrapper.eq(EviDataMainEntity::getStepId, dataReq.getStepId());
        }
        if (StringUtils.isNotBlank(dataReq.getUserName())) {
            wrapper.eq(EviDataMainEntity::getUserName, dataReq.getUserName());
        }
        wrapper.orderByDesc(EviDataMainEntity::getId);
        IPage<EviDataMainEntity> iPage = new Page<>(dataReq.getCurrentPage(), dataReq.getPageSize());
        IPage<EviDataMainEntity> dataMainPage = this.page(iPage, wrapper);
        IPage<DataMainResp> dataMainRespPage = convertDataPage(dataMainPage);
        return EviResponse.ok(dataMainRespPage);
    }

    @Override
    public EviResponse bqSave(DataSaveReq dataSaveReq) {
        String hashCal = HashCalEnum.SHA256.type;
        List<DataItemReq> itemReqList = dataSaveReq.getItemList();
        if (CollectionUtils.isEmpty(itemReqList)) {
            return EviResponse.error("存证处理失败");
        }
        Map<String, String> dataMap = itemReqList.stream().collect(Collectors.toMap(DataItemReq::getEnName, DataItemReq::getEnValue));
        String dataContent = JSONUtil.toJsonStr(dataMap);
        String dataHash = DigestUtil.sha256Hex(dataContent);
        EviDataMainEntity dataMainEntity = new EviDataMainEntity();
        BeanUtils.copyProperties(dataSaveReq, dataMainEntity);
        dataMainEntity.setDataHash(dataHash);
        dataMainEntity.setHashCal(hashCal);
        dataMainEntity.setDataJson(dataContent);
        dataMainEntity.setChainStatus(ChainStatusEnum.NO.code);
        dataMainEntity.setSaveTime(LocalDateTime.now());
        boolean flag = this.save(dataMainEntity);
        if (!flag) {
            return EviResponse.error("存证处理失败");
        }
        log.info("存证处理成功，开始进行数据上链,存证数据id={}", dataMainEntity.getId());
        uploadChain(dataMainEntity);
        List<DataFileReq> fileList = dataSaveReq.getAddedFileList();
        if (CollectionUtils.isEmpty(fileList)) {
            log.info("存证附件为空,无需处理");
            return EviResponse.ok();
        }
        scheduledExecutorService.execute(() -> handleDataFile(dataMainEntity.getId(),
                dataMainEntity.getSaveTime(), fileList));
        return EviResponse.ok();
    }

    @Override
    public EviResponse validChain(Integer dataId) {
        EviDataMainEntity dataMainEntity = this.getById(dataId);
        if (ObjectUtil.isNull(dataMainEntity) || StringUtils.isBlank(dataMainEntity.getChainAddress())) {
            log.info("待核验的数据不存在或未上链,dataId={}", dataId);
            return EviResponse.error("待核验的数据不存在或未上链");
        }
        String chainAddress = dataMainEntity.getChainAddress();
        String chainResponse = contractService.searchChain(chainAddress);
        if (StringUtils.isBlank(chainResponse)) {
            log.info("核验数据返回链上数据为空,dataId={},chainAddress={}", dataId, chainAddress);
            return EviResponse.error("核验数据返回链上数据为空");
        }
        ChainValidResp validResp = new ChainValidResp();
        ChainQueryData queryResp = parseChainData(chainResponse);
        validResp.setDataHash(queryResp.getDataHash());
        validResp.setHashCal(queryResp.getHashCal());
        validResp.setSaveTime(queryResp.getSaveTime());
        validResp.setChainAddress(chainAddress);
        validResp.setDataJson(dataMainEntity.getDataJson());
        List<FileValidResp> fileValidRespList = doFileValid(dataId);
        validResp.setFileValidRespList(fileValidRespList);
        return EviResponse.ok(validResp);
    }

    /**
     * 附件信息校验
     * @author yibi
     * @date 2021-06-28 19:33
     * @param dataId 存证数据id
     * @return java.util.List<com.yibi.evidence.chain.vo.resp.FileValidResp>
     */
    private List<FileValidResp> doFileValid(Integer dataId) {
        List<EviAttachEntity> attachEntityList = attachService.listByDataId(dataId);
        List<FileValidResp> fileValidRespList = Lists.newArrayListWithCapacity(attachEntityList.size());
        if (CollectionUtils.isEmpty(attachEntityList)) {
            return fileValidRespList;
        }
        attachEntityList.forEach((attachEntity) -> {
            FileValidResp fileValidResp = new FileValidResp();
            fileValidResp.setFileName(attachEntity.getAttachName());
            String chainAddress = attachEntity.getChainAddress();
            if (StringUtils.isBlank(chainAddress)) {
                fileValidRespList.add(fileValidResp);
                return;
            }
            String chainResponse = contractService.searchChain(chainAddress);
            if (StringUtils.isBlank(chainResponse)) {
                log.info("核验附件数据返回链上数据为空,fileId={},chainAddress={}", attachEntity.getId(), chainAddress);
                fileValidRespList.add(fileValidResp);
                return;
            }
            ChainQueryData queryResp = parseChainData(chainResponse);
            fileValidResp.setHashCal(queryResp.getHashCal());
            fileValidResp.setAttachHash(queryResp.getDataHash());
            fileValidResp.setChainAddress(chainAddress);
            fileValidRespList.add(fileValidResp);
        });
        return fileValidRespList;
    }

    @Override
    public EviResponse getTotalInfo() {
        TotalInfoResp totalInfoResp = new TotalInfoResp();
        Integer totalEviCount = this.count();
        LambdaQueryWrapper<EviDataMainEntity> dataQw = new LambdaQueryWrapper<>();
        dataQw.eq(EviDataMainEntity::getChainStatus, ChainStatusEnum.YES.code);
        Integer dataChainCount = this.count(dataQw);
        LambdaQueryWrapper<EviAttachEntity> attachQw = new LambdaQueryWrapper<>();
        attachQw.eq(EviAttachEntity::getChainStatus, ChainStatusEnum.YES.code);
        Integer attachChainCount = attachService.count(attachQw);
        Integer totalChainCount = dataChainCount + attachChainCount;
        totalInfoResp.setTotalChainCount(totalChainCount);
        totalInfoResp.setTotalEviCount(totalEviCount);
        return EviResponse.ok(totalInfoResp);
    }

    @Override
    public EviResponse previewChain(Integer dataId) {
        EviDataMainEntity dataMainEntity = this.getById(dataId);
        if (ObjectUtil.isNull(dataMainEntity)) {
            log.info("预览的存证数据不存在,dataId={}", dataId);
            return EviResponse.error("预览的存证数据不存在");
        }
        DataAndFilePreviewResp dataAndFilePreviewResp = new DataAndFilePreviewResp();
        List<DataPreviewResp> dataPreviewRespList = fieldService.previewFieldData(dataMainEntity.getStepId(),
                dataMainEntity.getDataJson());
        List<EviAttachEntity> attachEntityList = attachService.listByDataId(dataId);
        List<FilePreviewResp> filePreviewRespList = Lists.newArrayListWithCapacity(attachEntityList.size());
        attachEntityList.forEach((attachEntity) -> {
            FilePreviewResp filePreviewResp = new FilePreviewResp();
            filePreviewResp.setFileName(attachEntity.getAttachName());
            filePreviewResp.setChainAddress(attachEntity.getChainAddress());
            filePreviewRespList.add(filePreviewResp);
        });
        dataAndFilePreviewResp.setDataPreviewRespList(dataPreviewRespList);
        dataAndFilePreviewResp.setFilePreviewRespList(filePreviewRespList);
        return EviResponse.ok(dataAndFilePreviewResp);
    }

    private void handleDataFile(Integer dataId, LocalDateTime saveDateTime, List<DataFileReq> fileList) {
        fileList.forEach((fileReq) -> {
            String fileNo = fileReq.getFileNo();
            EviAttachEntity attachEntity = attachService.getByFileNo(fileNo);
            String attachHash = attachService.generateFileHash(attachEntity.getAttachPath());
            if (StringUtils.isBlank(attachHash)) {
                return;
            }
            String saveTime = DateUtils.formatDateTime(saveDateTime);
            String hashCal = HashCalEnum.SHA256.type;
            attachEntity.setHashCal(hashCal);
            attachEntity.setDataId(dataId);
            attachEntity.setAttachHash(attachHash);
            String dataAddress = contractService.uploadChain(attachHash, hashCal, saveTime);
            if (StringUtils.isBlank(dataAddress)) {
                attachEntity.setChainStatus(ChainStatusEnum.NO.code);
                log.error("附件上链失败,返回数据上链地址为空,附件编号={}", fileNo);
            } else {
                attachEntity.setChainStatus(ChainStatusEnum.YES.code);
                attachEntity.setChainAddress(dataAddress);
            }
            attachService.updateById(attachEntity);
        });
    }

    /**存证数据分页转换*/
    private IPage<DataMainResp> convertDataPage(IPage<EviDataMainEntity> dataMainPage) {
        IPage<DataMainResp> dataMainRespPage = new Page<>(dataMainPage.getCurrent(), dataMainPage.getSize());
        List<EviDataMainEntity> dataMainEntityList = dataMainPage.getRecords();
        List<DataMainResp> dataMainRespList = Lists.newArrayListWithCapacity(dataMainEntityList.size());
        dataMainEntityList.forEach((dataMainEntity) -> {
            DataMainResp mainResp = new DataMainResp();
            BeanUtils.copyProperties(dataMainEntity, mainResp);
            mainResp.setProductName(productService.getNameById(dataMainEntity.getProductId()));
            mainResp.setStepName(stepService.getNameById(dataMainEntity.getStepId()));
            mainResp.setSaveTimeStr(DateUtils.formatDateTime(dataMainEntity.getSaveTime()));
            dataMainRespList.add(mainResp);
        });
        dataMainRespPage.setRecords(dataMainRespList);
        dataMainRespPage.setTotal(dataMainPage.getTotal());
        return dataMainRespPage;
    }

    /**解析上链返回结果*/
    private ChainQueryData parseChainData(String chainResponse) {
        JSONArray jsonArray = JSONUtil.parseArray(chainResponse);
        String dataHash = jsonArray.get(0).toString();
        String hashCal = jsonArray.get(1).toString();
        String saveTime = jsonArray.get(2).toString();
        return new ChainQueryData(dataHash, hashCal, saveTime);
    }

    /**
     * 数据上链处理
     * @author yibi
     * @date 2021-06-25 11:09
     * @param dataMainEntity 存证数据信息
     */
    private void uploadChain(EviDataMainEntity dataMainEntity) {
        String dataHash = dataMainEntity.getDataHash();
        String hashCal = dataMainEntity.getHashCal();
        LocalDateTime saveDateTime = dataMainEntity.getSaveTime();
        String saveTime = DateUtils.formatDateTime(saveDateTime);
        String dataAddress = contractService.uploadChain(dataHash, hashCal, saveTime);
        if (StringUtils.isBlank(dataAddress)) {
            log.error("上链失败,返回数据上链地址为空,存证数据id={}", dataMainEntity.getId());
            return;
        }
        EviDataMainEntity dataMainEntityDb = new EviDataMainEntity();
        dataMainEntityDb.setId(dataMainEntity.getId());
        dataMainEntityDb.setChainStatus(ChainStatusEnum.YES.code);
        dataMainEntityDb.setChainAddress(dataAddress);
        this.updateById(dataMainEntityDb);
    }
}
