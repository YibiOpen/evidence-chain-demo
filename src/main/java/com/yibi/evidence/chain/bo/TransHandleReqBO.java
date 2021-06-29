package com.yibi.evidence.chain.bo;

import java.util.List;

/**
 * 上链请求实体类
 * @author yibi
 * @date 2021-06-25 12:37
 */
public class TransHandleReqBO {

    private String signUserId;

    private String user;

    private String contractName;

    private String contractAddress;

    private String funcName;

    private List contractAbi;

    private List funcParam;

    private Integer groupId;

    private Boolean useCns;

    public String getSignUserId() {
        return signUserId;
    }

    public void setSignUserId(String signUserId) {
        this.signUserId = signUserId;
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

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public List getContractAbi() {
        return contractAbi;
    }

    public void setContractAbi(List contractAbi) {
        this.contractAbi = contractAbi;
    }

    public List getFuncParam() {
        return funcParam;
    }

    public void setFuncParam(List funcParam) {
        this.funcParam = funcParam;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Boolean getUseCns() {
        return useCns;
    }

    public void setUseCns(Boolean useCns) {
        this.useCns = useCns;
    }

    @Override
    public String toString() {
        return "TransHandleReqBO{" +
                "signUserId='" + signUserId + '\'' +
                ", user='" + user + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", funcName='" + funcName + '\'' +
                ", contractAbi=" + contractAbi +
                ", funcParam=" + funcParam +
                ", groupId=" + groupId +
                ", useCns=" + useCns +
                '}';
    }
}
