package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 * 要素修改实体
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class FieldModifyReq {
    /**要素id*/
    private Integer id;
    /**要素英文名称*/
    private String enName;
    /**要素中文名称*/
    private String chName;
}
