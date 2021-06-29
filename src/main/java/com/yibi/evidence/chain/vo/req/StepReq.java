package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 *节点请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class StepReq extends EviPage {
    /**节点id*/
    private Integer id;
    /**节点名称*/
    private String stepName;
    /**产品id*/
    private Integer productId;
}
