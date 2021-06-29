package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *证据要素返回内容
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class EviFieldResp {

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
     * 字段英文名称
     */
    private String enName;

    /**
     * 字段中文名称
     */
    private String chName;

    /**
     * 创建时间
     */
    private String createTimeStr;
}
