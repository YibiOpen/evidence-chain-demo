package com.yibi.evidence.chain.config;

import cn.hutool.core.lang.Pair;
import cn.hutool.json.JSONUtil;
import com.webank.webase.app.sdk.client.AppClient;
import com.webank.webase.app.sdk.config.HttpConfig;
import com.webank.webase.app.sdk.dto.req.ReqAppRegister;
import com.webank.webase.app.sdk.dto.req.ReqContractAddressSave;
import com.webank.webase.app.sdk.dto.req.ReqContractSourceSave;
import com.webank.webase.app.sdk.dto.req.ReqNewUser;
import com.webank.webase.app.sdk.dto.rsp.RspBasicInfo;
import com.webank.webase.app.sdk.dto.rsp.RspUserInfo;
import com.yibi.evidence.chain.constant.WebaseConstant;
import com.yibi.evidence.chain.contract.Evidence;
import com.yibi.evidence.chain.contract.EvidenceSignersData;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *webase客户端
 *@author yibi
 *@date 2021-06-24
 */
@Slf4j
@Component
public class WebaseClient implements ApplicationListener<ApplicationReadyEvent> {
    /**默认超时时间为30s*/
    private static final Integer DEFAULT_TIMEOUT = 30;

    private final WebaseConfig webaseConfig;
    private final AppClient appClient;

    public WebaseClient(WebaseConfig webaseConfig) {
        this.webaseConfig = webaseConfig;
        HttpConfig httpConfig = new HttpConfig(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
        this.appClient = new AppClient(webaseConfig.getUrl(), webaseConfig.getAppKey(), webaseConfig.getAppSecret(),
                webaseConfig.isTransferEncrypt(), httpConfig);
    }

    /**
     * 查询是否为国密
     * @author yibi
     * @date 2021-06-27 23:17
     * @return boolean
     */
    public boolean isSmMode() {
        RspBasicInfo basicInfo = appClient.basicInfo();
        if (null == basicInfo || WebaseConstant.SM_ENCRYPT_TYPE.equals(basicInfo.getEncryptType())) {
            return true;
        }
        return false;
    }

    /**
     * 节点基本信息
     * @author yibi
     * @date 2021-06-27 23:17
     * @return boolean
     */
    public RspBasicInfo basicInfo() {
        return appClient.basicInfo();
    }

    /**
     * 应用注册
     * @author yibi
     * @date 2021-06-24 14:23
     */
    public EviResponse appRegister() {
        ReqAppRegister req = new ReqAppRegister();
        String linkUrl = this.webaseConfig.getLinkUrl();
        Pair<String, Integer> hostAndPort = UrlUtils.getHostAndPort(linkUrl);
        if (null == hostAndPort) {
            return EviResponse.error("配置应用注册链接不正确，format example:[http://{ip}:{port}/index.html]");
        }
        req.setAppIp(hostAndPort.getKey());
        req.setAppPort(hostAndPort.getValue());
        req.setAppLink(linkUrl);
        this.appClient.appRegister(req);
        return EviResponse.ok();
    }

    /**
     * 新增私钥用户
     * @author yibi
     * @date 2021-06-24 15:02
     * @param userName 用户名称
     * @return com.webank.webase.app.sdk.dto.rsp.RspUserInfo
     */
    public RspUserInfo newUser(String userName) {
        ReqNewUser reqNewUser = new ReqNewUser();
        reqNewUser.setUserName(userName);
        reqNewUser.setAccount(WebaseConstant.ACCOUNT);
        reqNewUser.setGroupId(webaseConfig.getGroupId());
        reqNewUser.setDescription("区块链电子存证");
        RspUserInfo userInfo = appClient.newUser(reqNewUser);
        log.info("新增私钥用户={}返回结果为={}", userName, userInfo);
        return userInfo;
    }

    /**
     * 返回群组id
     * @author yibi
     * @date 2021-06-24 16:57
     * @return java.lang.Integer
     */
    public Integer getGroupId() {
        return webaseConfig.getGroupId();
    }

    /**
     * 合约同步
     * @author yibi
     * @date 2021-06-24 17:09
     */
    public void contractSourceSave() {
        ReqContractSourceSave reqContractSourceSave = new ReqContractSourceSave();
        reqContractSourceSave.setAccount(WebaseConstant.ACCOUNT);
        reqContractSourceSave.setContractVersion(WebaseConstant.CONTRACT_VERSION);
        List<ReqContractSourceSave.ContractSource> contractList = new ArrayList<>();

        // add EvidenceSignersData contract
        ReqContractSourceSave.ContractSource evidenceFactoryContractSource = new ReqContractSourceSave.ContractSource();
        evidenceFactoryContractSource.setContractName(WebaseConstant.EVIDENCE_CONTRACT_DATA_NAME);
        evidenceFactoryContractSource.setContractSource(EvidenceSignersData.SOURCE_BASE64);
        evidenceFactoryContractSource.setContractAbi(EvidenceSignersData.ABI);
        evidenceFactoryContractSource.setBytecodeBin(EvidenceSignersData.SM_BINARY);

        // add Evidence contract
        ReqContractSourceSave.ContractSource evidenceContractSource = new ReqContractSourceSave.ContractSource();
        evidenceContractSource.setContractName(WebaseConstant.EVIDENCE_CONTRACT_NAME);
        evidenceContractSource.setContractSource(Evidence.SOURCE_BASE64);
        evidenceContractSource.setContractAbi(Evidence.ABI);
        evidenceContractSource.setBytecodeBin(Evidence.SM_BINARY);

        contractList.add(evidenceContractSource);
        contractList.add(evidenceFactoryContractSource);
        reqContractSourceSave.setContractList(contractList);
        log.info("调用WebaseSdk ContractSourceSave接口,请求参数:>>{}", JSONUtil.toJsonStr(reqContractSourceSave));
        appClient.contractSourceSave(reqContractSourceSave);
    }

    /**
     * 合约地址绑定
     * @author yibi
     * @date 2021-06-24 17:08
     * @param contractAddr 合约地址
     */
    public void contractAddressSave(String contractAddr) {
        ReqContractAddressSave reqContractAddressSave = new ReqContractAddressSave();
        reqContractAddressSave.setGroupId(webaseConfig.getGroupId());
        reqContractAddressSave.setContractName(WebaseConstant.EVIDENCE_CONTRACT_DATA_NAME);
        reqContractAddressSave.setContractPath(WebaseConstant.EVIDENCE_CONTRACT_NAME + "_" + DateUtils.dateTimeNow());
        reqContractAddressSave.setContractVersion(WebaseConstant.CONTRACT_VERSION);
        reqContractAddressSave.setContractAddress(contractAddr);
        log.info("调用WebaseSdk AddressSave接口,请求参数:>>{}", JSONUtil.toJsonStr(reqContractAddressSave));
        appClient.contractAddressSave(reqContractAddressSave);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // register
        this.appRegister();
    }
}
