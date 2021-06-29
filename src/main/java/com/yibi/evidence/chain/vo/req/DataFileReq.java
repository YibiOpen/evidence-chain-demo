package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 * 存证附件实体
 *@author yibi
 *@date 2021-06-26
 */
@Data
public class DataFileReq {
    /**存证附件名称*/
    private String fileName;
    /**附件编号*/
    private String fileNo;
}
