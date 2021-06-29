package com.yibi.evidence.chain.bo;

import java.util.List;

/**
 * 合约部署实体类
 * @author yibi
 * @date 2021-06-25 12:36
 */
public class ContractDeployReqBO {

    private Integer groupId;

    private String user;

    private String contractName;

    private List abiInfo;

    private String bytecodeBin;

    private List funcParam;

    private String signUserId;

    private String contractSource;

    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getContractSource() {
        return contractSource;
    }

    public void setContractSource(String contractSource) {
        this.contractSource = contractSource;
    }

    public String getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(String signUserId) {
        this.signUserId = signUserId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


    public String getBytecodeBin() {
        return bytecodeBin;
    }

    public void setBytecodeBin(String bytecodeBin) {
        this.bytecodeBin = bytecodeBin;
    }

    public List getAbiInfo() {
        return abiInfo;
    }

    public void setAbiInfo(List abiInfo) {
        this.abiInfo = abiInfo;
    }

    public List getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(List funcParam) {
        this.funcParam = funcParam;
    }

    @Override
    public String toString() {
        return "ContractDeployReqBO{" +
                "groupId=" + groupId +
                ", user='" + user + '\'' +
                ", contractName='" + contractName + '\'' +
                ", abiInfo=" + abiInfo +
                ", bytecodeBin='" + bytecodeBin + '\'' +
                ", funcParam=" + funcParam +
                ", signUserId='" + signUserId + '\'' +
                ", contractSource='" + contractSource + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
