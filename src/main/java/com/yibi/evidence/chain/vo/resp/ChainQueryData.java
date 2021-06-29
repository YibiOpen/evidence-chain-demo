package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 * 链上查询返回实体
 * @author yibi
 * @date 2021-06-25 11:33
 */
@Data
public class ChainQueryData {
    /**数据hash*/
    private String dataHash;
    /**hash算法*/
    private String hashCal;
    /**存证时间*/
    private String saveTime;

    public ChainQueryData(String dataHash, String hashCal, String saveTime) {
        this.dataHash = dataHash;
        this.hashCal = hashCal;
        this.saveTime = saveTime;
    }
}
