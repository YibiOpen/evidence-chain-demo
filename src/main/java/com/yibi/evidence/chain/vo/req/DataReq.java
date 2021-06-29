package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 *查询存证数据请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class DataReq extends EviPage {
    /**产品id*/
    private Integer productId;
    /**节点id*/
    private Integer stepId;
    /**用户名称*/
    private String userName;
}
