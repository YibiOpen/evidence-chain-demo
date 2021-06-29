package com.yibi.evidence.chain.bo;

/**
 * 链上交易查询请求类
 * @author yibi
 * @date 2021-06-25 9:42
 */
public class TransQueryReqBO {

    /**已编码字符串*/
    private String encodeStr;
    /**合约地址*/
    private String contractAddress;
    /**群组ID*/
    private Integer groupId;
    /**合约方法名*/
    private String funcName;
    /**合约abi*/
    private String contractAbi;
    /**用户地址*/
    private String userAddress;

    public String getEncodeStr() {
        return encodeStr;
    }

    public void setEncodeStr(String encodeStr) {
        this.encodeStr = encodeStr;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getFuncName() {
        return funcName;
    }

    public void setFuncName(String funcName) {
        this.funcName = funcName;
    }

    public String getContractAbi() {
        return contractAbi;
    }

    public void setContractAbi(String contractAbi) {
        this.contractAbi = contractAbi;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString() {
        return "TransQueryReqBO{" +
                "encodeStr='" + encodeStr + '\'' +
                ", contractAddress='" + contractAddress + '\'' +
                ", groupId=" + groupId +
                ", funcName='" + funcName + '\'' +
                ", contractAbi='" + contractAbi + '\'' +
                ", userAddress='" + userAddress + '\'' +
                '}';
    }
}
