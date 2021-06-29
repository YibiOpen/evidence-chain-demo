package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *存证数据返回内容
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class DataMainResp {

    private Integer id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 环节名称
     */
    private String stepName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 证件编号
     */
    private String identNo;

    /**
     * 证件类型
     */
    private String identType;

    /**
     * 数据hash
     */
    private String dataHash;

    /**
     * hash算法
     */
    private String hashCal;

    /**
     * 存证时间
     */
    private String saveTimeStr;

    /**
     * 上链地址
     */
    private Integer chainStatus;

    /**
     * 上链地址
     */
    private String chainAddress;
}
