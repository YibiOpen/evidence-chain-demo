package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *节点返回内容
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class EviStepResp {

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
     * 环节名称
     */
    private String stepCode;
    
    /**
     * 接入状态：1.正常、-1.停止
     */
    private Integer openStatus;

    /**
     * 创建时间
     */
    private String createTimeStr;
}
