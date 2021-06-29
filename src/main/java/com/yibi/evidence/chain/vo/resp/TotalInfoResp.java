package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *存证上链统计信息
 *@author yibi
 *@date 2021-06-25
 */
@Data
public class TotalInfoResp {
    /**存证总数量*/
    private Integer totalEviCount;
    /**上链总数量*/
    private Integer totalChainCount;
}
