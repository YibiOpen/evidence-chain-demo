package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 *产品请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class ProductReq extends EviPage {
    /**产品id*/
    private Integer id;
    /**产品名称*/
    private String productName;
}
