package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 * 区块链信息返回实体
 * @author yibi
 * @date 2021-06-25 11:33
 */
@Data
public class ChainInfoResp {
    /**初始状态:true已初始化、false未初始化*/
    private boolean initStatus;
    /** 国密|非国密*/
    private Integer encryptType;
    /**FISCO-BCOS版本*/
    private String fiscoBcosVersion;
    /**WeBASE版本*/
    private String webaseVersion;
}
