package com.yibi.evidence.chain.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.webank.webase.app.sdk.dto.rsp.RspBasicInfo;
import com.webank.webase.app.sdk.dto.rsp.RspUserInfo;
import com.yibi.evidence.chain.bo.ContractDeployReqBO;
import com.yibi.evidence.chain.config.WebaseClient;
import com.yibi.evidence.chain.constant.WebaseConstant;
import com.yibi.evidence.chain.contract.EvidenceSignersData;
import com.yibi.evidence.chain.persist.entity.EviContractEntity;
import com.yibi.evidence.chain.persist.entity.EviUserEntity;
import com.yibi.evidence.chain.persist.mapper.IEviUserMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviContractService;
import com.yibi.evidence.chain.service.EviUserService;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.util.HttpUtils;
import com.yibi.evidence.chain.vo.req.LoginReq;
import com.yibi.evidence.chain.vo.resp.ChainInfoResp;
import com.yibi.evidence.chain.vo.resp.LoginResp;
import com.yibi.evidence.chain.vo.resp.UserAndChainResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 管理用户表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviUserServiceImpl extends ServiceImpl<IEviUserMapper, EviUserEntity> implements EviUserService {

    @Resource
    private WebaseClient webaseClient;
    @Resource
    private EviContractService contractService;

    @Value("${webase-front.contract.deploy.url}")
    private String webaseFrontContractDeployUrl;

    @Override
    public EviResponse login(LoginReq loginReq) {
        LambdaQueryWrapper<EviUserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviUserEntity::getUname, loginReq.getUsername());
        EviUserEntity userEntity = getOne(wrapper);
        if (ObjectUtil.isNull(userEntity)) {
            return EviResponse.error("用户名或密码不正确");
        }
        String encryptedPassword = DigestUtils.sha256Hex(loginReq.getEncryptedPassword());
        if (!userEntity.getPwd().equals(encryptedPassword)) {
            return EviResponse.error("用户名或密码不正确");
        }
        LoginResp loginResp = new LoginResp();
        loginResp.setUserId(userEntity.getId());
        return EviResponse.ok(loginResp);
    }


    @Override
    public EviResponse deployContract(Integer userId) {
        EviUserEntity userEntity = registerWb(userId);
        if (ObjectUtil.isNull(userEntity)) {
            return EviResponse.error("存证合约部署失败");
        }
        ContractDeployReqBO contractDeployReqBO = new ContractDeployReqBO();
        String contractName = WebaseConstant.EVIDENCE_CONTRACT_NAME + "_" + DateUtils.dateTimeNow();
        JSONArray parseArray = JSONUtil.parseArray(EvidenceSignersData.ABI);
        List<Object> abiList = JSONUtil.toList(parseArray, Object.class);
        contractDeployReqBO.setGroupId(webaseClient.getGroupId());
        contractDeployReqBO.setAbiInfo(abiList);
        if (webaseClient.isSmMode()) {
            contractDeployReqBO.setBytecodeBin(EvidenceSignersData.SM_BINARY);
        } else {
            contractDeployReqBO.setBytecodeBin(EvidenceSignersData.BINARY);
        }
        contractDeployReqBO.setContractName(contractName);
        contractDeployReqBO.setVersion(WebaseConstant.CONTRACT_VERSION);

        List<Object> list = new ArrayList<>();
        List<String> signerAddrs = new ArrayList<>();
        signerAddrs.add(userEntity.getPublicKey());
        signerAddrs.add(WebaseConstant.SIGN_PUBLIC_KEY1);
        signerAddrs.add(WebaseConstant.SIGN_PUBLIC_KEY2);
        list.add(signerAddrs);

        contractDeployReqBO.setFuncParam(list);
        contractDeployReqBO.setSignUserId(userEntity.getSignUserId());
        String response = HttpUtils.httpPostByJson(webaseFrontContractDeployUrl, JSONUtil.toJsonStr(contractDeployReqBO));
        log.info("进行存证合约部署返回结果为={}", response);
        webaseClient.contractSourceSave();
        webaseClient.contractAddressSave(response);
        contractService.insertContract(contractName, userEntity, response);
        return EviResponse.ok(response);
    }

    @Override
    public EviResponse chainInfo() {
        ChainInfoResp infoResp = new ChainInfoResp();
        EviContractEntity contractEntity = contractService.getEviContract();
        if (ObjectUtil.isNotNull(contractEntity)) {
            infoResp.setInitStatus(true);
            return EviResponse.ok(infoResp);
        }
        RspBasicInfo basicInfo = webaseClient.basicInfo();
        if (null == basicInfo) {
            return EviResponse.error("区块链节点信息查询失败");
        }
        infoResp.setInitStatus(false);
        infoResp.setEncryptType(basicInfo.getEncryptType());
        infoResp.setFiscoBcosVersion(basicInfo.getFiscoBcosVersion());
        infoResp.setWebaseVersion(basicInfo.getWebaseVersion());
        return EviResponse.ok(infoResp);
    }

    @Override
    public EviResponse getInfoAndChain(Integer userId) {
        EviUserEntity userEntity = this.getById(userId);
        EviContractEntity contractEntity = contractService.getEviContract();
        UserAndChainResp chainResp = new UserAndChainResp();
        BeanUtils.copyProperties(userEntity, chainResp);
        chainResp.setContractName(contractEntity.getContractName());
        chainResp.setContractAddress(contractEntity.getContractAddress());
        chainResp.setCreateTimeStr(DateUtils.formatDateTime(contractEntity.getCreateTime()));
        return EviResponse.ok(chainResp);
    }

    /**
     * webase应用注册
     * @author yibi
     * @date 2021-06-28 11:19
     * @param userId 用户id
     * @return com.yibi.evidence.chain.persist.entity.EviUserEntity
     */
    private EviUserEntity registerWb(Integer userId) {
        EviUserEntity userEntity = this.getById(userId);
        if (StringUtils.isNotBlank(userEntity.getPublicKey())) {
            return userEntity;
        }
        EviResponse response = webaseClient.appRegister();
        if (!EviResponse.isOk(response)) {
            return null;
        }
        String webaseUserName = userEntity.getUname() + System.currentTimeMillis();
        RspUserInfo userInfo = webaseClient.newUser(webaseUserName);
        if (ObjectUtil.isNull(userInfo)) {
            log.warn("新增用户私钥失败,userId={}", userId);
            return null;
        }
        userEntity.setSignUserId(userInfo.getSignUserId());
        userEntity.setPublicKey(userInfo.getAddress());
        userEntity.setPrivateKey(userInfo.getPrivateKey());
        this.updateById(userEntity);
        return userEntity;
    }
}
