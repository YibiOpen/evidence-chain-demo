package com.yibi.evidence.chain.vo.req;

import lombok.Data;

import java.util.List;

/**
 *存证要素添加请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class FieldAddReq {
    /**产品id*/
    private Integer productId;
    /**节点id*/
    private Integer stepId;
    /**存证数据*/
    private List<FieldItemReq> itemList;
}
