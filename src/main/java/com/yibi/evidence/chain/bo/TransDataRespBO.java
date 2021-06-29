package com.yibi.evidence.chain.bo;

import java.util.List;

/**
 * 上链交易返回结果
 * @author yibi
 * @date 2021-06-25 9:15
 */
public class TransDataRespBO {
    /**区块链交易hash*/
    private String transactionHash;
    /**区块链交易处理结果*/
    private Boolean statusOK;
    /**区块链交易合约返回信息列表*/
    private List<LogAddressBO> logs;

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public List<LogAddressBO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogAddressBO> logs) {
        this.logs = logs;
    }

    public boolean isStatusOK() {
        return statusOK;
    }

    public void setStatusOK(boolean statusOK) {
        this.statusOK = statusOK;
    }

    @Override
    public String toString() {
        return "TransDataRespBO{" +
                "transactionHash='" + transactionHash + '\'' +
                ", statusOK=" + statusOK +
                ", logs=" + logs +
                '}';
    }
}
