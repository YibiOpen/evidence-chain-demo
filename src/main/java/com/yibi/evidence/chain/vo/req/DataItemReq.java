package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 * 存证要素实体
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class DataItemReq {
    /**要素英文名称*/
    private String enName;
    /**要素值*/
    private String enValue;
}
