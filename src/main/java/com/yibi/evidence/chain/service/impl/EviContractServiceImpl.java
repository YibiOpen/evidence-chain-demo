package com.yibi.evidence.chain.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibi.evidence.chain.bo.TransDataRespBO;
import com.yibi.evidence.chain.bo.TransHandleReqBO;
import com.yibi.evidence.chain.bo.TransQueryReqBO;
import com.yibi.evidence.chain.config.WebaseClient;
import com.yibi.evidence.chain.constant.WebaseConstant;
import com.yibi.evidence.chain.contract.Evidence;
import com.yibi.evidence.chain.contract.EvidenceSignersData;
import com.yibi.evidence.chain.persist.entity.EviContractEntity;
import com.yibi.evidence.chain.persist.entity.EviUserEntity;
import com.yibi.evidence.chain.persist.mapper.IEviContractMapper;
import com.yibi.evidence.chain.service.EviContractService;
import com.yibi.evidence.chain.util.HttpUtils;
import com.yibi.evidence.chain.util.WebaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.web3j.crypto.Sign;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 存证合约表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviContractServiceImpl extends ServiceImpl<IEviContractMapper, EviContractEntity> implements EviContractService {

    @Value("${webase-front.trans.handle.url}")
    private String webaseFrontTransHandleUrl;
    @Value("${webase-front.trans.query.url}")
    private String webaseFrontTransQueryUrl;
    @Resource
    private WebaseClient webaseClient;

    @Override
    public void insertContract(String contractName, EviUserEntity userEntity, String contractAddress) {
        EviContractEntity contractEntity = new EviContractEntity();
        contractEntity.setContractName(contractName);
        contractEntity.setContractAddress(contractAddress);
        contractEntity.setSignUserId(userEntity.getSignUserId());
        contractEntity.setPrivateKey(userEntity.getPrivateKey());
        contractEntity.setPublicKey(userEntity.getPublicKey());
        this.save(contractEntity);
    }

    @Override
    public String uploadChain(String dataHash, String hashCal, String saveTime) {
        EviContractEntity contractEntity = getEviContract();
        if (ObjectUtil.isNull(contractEntity)) {
            log.error("还未部署存证合约,无法进行数据上链");
            return null;
        }
        TransHandleReqBO transHandleReqBO = new TransHandleReqBO();
        JSONArray parseArray = JSONUtil.parseArray(EvidenceSignersData.ABI);
        List<Object> abiList = JSONUtil.toList(parseArray, Object.class);
        transHandleReqBO.setContractAbi(abiList);
        transHandleReqBO.setContractAddress(contractEntity.getContractAddress());
        transHandleReqBO.setContractName(contractEntity.getContractName());
        transHandleReqBO.setFuncName(EvidenceSignersData.FUNC_NEWEVIDENCE);
        List<Object> params = getList(dataHash, hashCal, saveTime, contractEntity);
        if (params == null) {
            return null;
        }
        transHandleReqBO.setFuncParam(params);
        transHandleReqBO.setGroupId(webaseClient.getGroupId());
        transHandleReqBO.setUseCns(false);
        transHandleReqBO.setSignUserId(contractEntity.getSignUserId());
        log.info("调用webase-front接口,url>>{},请求参数:>>{}", webaseFrontTransHandleUrl, JSONUtil.toJsonStr(transHandleReqBO));
        String resp = HttpUtils.httpPostByJson(webaseFrontTransHandleUrl, JSONUtil.toJsonStr(transHandleReqBO));
        log.info("调用webase-front接口,响应结果reslut:>>{}", resp);
        TransDataRespBO resBO = JSONUtil.toBean(resp, TransDataRespBO.class);
        if (ObjectUtil.isNull(resBO) || !resBO.isStatusOK()) {
            log.error("存证数据上链返回失败");
            return null;
        }
        return resBO.getLogs().get(0).getAddress();
    }

    @Override
    public String searchChain(String dataAddress) {
        EviContractEntity contractEntity = getEviContract();
        if (ObjectUtil.isNull(contractEntity)) {
            log.error("还未部署存证合约,无法进行上链数据查询");
            return null;
        }
        TransQueryReqBO transQueryReqBO = new TransQueryReqBO();
        transQueryReqBO.setUserAddress(contractEntity.getPublicKey());
        transQueryReqBO.setEncodeStr(WebaseConstant.GET_EVIDENCE_ENCODE);
        transQueryReqBO.setContractAbi(Evidence.ABI);
        transQueryReqBO.setContractAddress(dataAddress);
        transQueryReqBO.setFuncName(Evidence.FUNC_GETEVIDENCE);
        transQueryReqBO.setGroupId(webaseClient.getGroupId());
        log.info("调用webase-front接口,url>>{},请求参数:>>{}", webaseFrontTransQueryUrl, JSONUtil.toJsonStr(transQueryReqBO));
        String resp = HttpUtils.httpPostByJson(webaseFrontTransQueryUrl, JSONUtil.toJsonStr(transQueryReqBO));
        log.info("调用webase-front接口,响应结果reslut:>>{}", resp);
        return resp;
    }

    /**上链合约参数组装*/
    @Nullable
    private List<Object> getList(String dataHash, String hashCal, String saveTime, EviContractEntity contractEntity) {
        Sign.SignatureData signatureData = WebaseUtils.signMessage(dataHash, contractEntity.getPrivateKey());
        if (ObjectUtil.isNull(signatureData)) {
            log.error("数据hash签名失败,无法进行数据上链");
            return null;
        }
        List<Object> params = new ArrayList<>();
        params.add(dataHash);
        params.add(hashCal);
        params.add(saveTime);
        params.add(signatureData.getV());
        params.add(WebaseUtils.bytesToHexString(signatureData.getR()));
        params.add(WebaseUtils.bytesToHexString(signatureData.getS()));
        return params;
    }

    /**
     * 获取存证合约
     * @author yibi
     * @date 2021-06-24 18:08
     * @return com.yibi.evidence.chain.persist.entity.EviContractEntity
     */
    @Override
    public EviContractEntity getEviContract() {
        List<EviContractEntity> contractEntities = this.list();
        if (CollectionUtils.isEmpty(contractEntities)) {
            return null;
        }
        return contractEntities.get(0);
    }
}
