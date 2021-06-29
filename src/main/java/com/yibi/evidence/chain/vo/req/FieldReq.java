package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 *要素请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class FieldReq extends EviPage {
    /**产品id*/
    private Integer productId;
    /**节点id*/
    private Integer stepId;
    /**要素字段关键字*/
    private String keyword;
}
