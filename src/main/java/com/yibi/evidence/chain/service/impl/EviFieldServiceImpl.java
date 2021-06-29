package com.yibi.evidence.chain.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yibi.evidence.chain.persist.entity.EviFieldEntity;
import com.yibi.evidence.chain.persist.mapper.IEviFieldMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviFieldService;
import com.yibi.evidence.chain.service.EviProductService;
import com.yibi.evidence.chain.service.EviStepService;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.vo.req.FieldAddReq;
import com.yibi.evidence.chain.vo.req.FieldItemReq;
import com.yibi.evidence.chain.vo.req.FieldModifyReq;
import com.yibi.evidence.chain.vo.req.FieldReq;
import com.yibi.evidence.chain.vo.resp.DataPreviewResp;
import com.yibi.evidence.chain.vo.resp.EviFieldResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * <p>
 * 配置要素表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviFieldServiceImpl extends ServiceImpl<IEviFieldMapper, EviFieldEntity> implements EviFieldService {

    @Resource
    private EviProductService productService;
    @Resource
    private EviStepService stepService;

    private static final String SN_NO_EN_NAME = "snNo";
    private static final String SN_NO_CH_NAME = "存证流水号";

    @Override
    public EviResponse fieldList(FieldReq fieldReq) {
        LambdaQueryWrapper<EviFieldEntity> wrapper = new LambdaQueryWrapper<>();
        if (null != fieldReq.getProductId()) {
            wrapper.eq(EviFieldEntity::getProductId, fieldReq.getProductId());
        }
        if (null != fieldReq.getStepId()) {
            wrapper.eq(EviFieldEntity::getStepId, fieldReq.getStepId());
        }
        String keyword = fieldReq.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            wrapper.and(o -> o.eq(EviFieldEntity::getChName, keyword).or().eq(EviFieldEntity::getEnName, keyword));
        }
        wrapper.orderByDesc(EviFieldEntity::getId);
        IPage<EviFieldEntity> iPage = new Page<>(fieldReq.getCurrentPage(), fieldReq.getPageSize());
        IPage<EviFieldEntity> fieldPage = this.page(iPage, wrapper);
        IPage<EviFieldResp> fieldRespPage = convertFieldPage(fieldPage);
        return EviResponse.ok(fieldRespPage);
    }

    @Override
    public EviResponse selectList(Integer stepId) {
        List<EviFieldEntity> fieldList = listByStepId(stepId);
        return EviResponse.ok(fieldList);
    }

    @Override
    public List<DataPreviewResp> previewFieldData(Integer stepId, String dataJson) {
        List<EviFieldEntity> fieldList = listByStepId(stepId);
        JSONObject dataObj = JSONUtil.parseObj(dataJson);
        List<DataPreviewResp> previewRespList = Lists.newArrayListWithCapacity(dataObj.size());
        Map<String, String> fieldMap = fieldList.stream().collect(Collectors.toMap(EviFieldEntity::getEnName,
                EviFieldEntity::getChName));
        dataObj.forEach((enName, value) -> {
            String enValue = value.toString();
            String chName = fieldMap.get(enName);
            if (StringUtils.isBlank(chName) && SN_NO_EN_NAME.equals(enName)) {
                chName = SN_NO_CH_NAME;
            }
            DataPreviewResp previewResp = new DataPreviewResp(chName, enName, enValue);
            previewRespList.add(previewResp);
        });
        return previewRespList;
    }

    @Override
    public EviResponse addFields(FieldAddReq fieldAddReq) {
        Integer productId = fieldAddReq.getProductId();
        Integer stepId = fieldAddReq.getStepId();
        List<FieldItemReq> fieldItemReqList = fieldAddReq.getItemList();
        AtomicBoolean addFlag = new AtomicBoolean(false);
        fieldItemReqList.forEach((fieldItemReq) -> {
            boolean flag = doAddField(productId, stepId, fieldItemReq);
            if (flag) {
                addFlag.set(true);
            }
        });
        if (addFlag.get()) {
            return EviResponse.ok();
        } else {
            return EviResponse.error("添加存证要素失败");
        }
    }

    @Override
    public EviResponse modifyField(FieldModifyReq fieldModifyReq) {
        String chName = fieldModifyReq.getChName();
        String enName = fieldModifyReq.getEnName();
        LambdaQueryWrapper<EviFieldEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(EviFieldEntity::getId, fieldModifyReq.getId());
        wrapper.and(o -> o.eq(EviFieldEntity::getChName, chName).or().eq(EviFieldEntity::getEnName, enName));
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("id={},要素中文={},要素英文={}已存在,不能修改", fieldModifyReq.getId(), chName, enName);
            return EviResponse.error("要素参数已存在,无法进行修改");
        }
        EviFieldEntity fieldEntity = new EviFieldEntity();
        BeanUtils.copyProperties(fieldModifyReq, fieldEntity);
        this.updateById(fieldEntity);
        return EviResponse.ok();
    }

    /**要素数据分页转换*/
    private IPage<EviFieldResp> convertFieldPage(IPage<EviFieldEntity> fieldPage) {
        IPage<EviFieldResp> fieldRespPage = new Page<>(fieldPage.getCurrent(), fieldPage.getSize());
        List<EviFieldEntity> fieldEntityList = fieldPage.getRecords();
        List<EviFieldResp> fieldRespList = Lists.newArrayListWithCapacity(fieldEntityList.size());
        fieldEntityList.forEach((fieldEntity) -> {
            EviFieldResp fieldResp = new EviFieldResp();
            BeanUtils.copyProperties(fieldEntity, fieldResp);
            fieldResp.setProductName(productService.getNameById(fieldEntity.getProductId()));
            fieldResp.setStepName(stepService.getNameById(fieldEntity.getStepId()));
            fieldResp.setCreateTimeStr(DateUtils.formatDateTime(fieldEntity.getCreateTime()));
            fieldRespList.add(fieldResp);
        });
        fieldRespPage.setRecords(fieldRespList);
        fieldRespPage.setTotal(fieldPage.getTotal());
        return fieldRespPage;
    }

    /**
     * 要素添加
     * @author yibi
     * @date 2021-06-27 18:35
     * @param productId 产品id
     * @param stepId 保全点id
     * @param fieldItemReq 要素项
     */
    private boolean doAddField(Integer productId, Integer stepId, FieldItemReq fieldItemReq) {
        String chName = fieldItemReq.getChName();
        String enName = fieldItemReq.getEnName();
        LambdaQueryWrapper<EviFieldEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviFieldEntity::getProductId, productId);
        wrapper.eq(EviFieldEntity::getStepId, stepId);
        wrapper.and(o -> o.eq(EviFieldEntity::getChName, chName).or().eq(EviFieldEntity::getEnName, enName));
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("productId={},stepId={},要素中文={},要素英文={}已存在,不能重复添加", productId, stepId, chName, enName);
            return false;
        }
        EviFieldEntity fieldEntity = new EviFieldEntity();
        fieldEntity.setProductId(productId);
        fieldEntity.setStepId(stepId);
        fieldEntity.setChName(chName);
        fieldEntity.setEnName(enName);
        return this.save(fieldEntity);
    }

    /**
     * 通过节点id获取要素列表
     * @author yibi
     * @date 2021-06-27 14:57
     * @param stepId 节点id
     * @return java.util.List<com.yibi.evidence.chain.persist.entity.EviFieldEntity>
     */
    private List<EviFieldEntity> listByStepId(Integer stepId) {
        LambdaQueryWrapper<EviFieldEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviFieldEntity::getStepId, stepId);
        return this.list(wrapper);
    }
}
