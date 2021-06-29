package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 * 用户和链信息返回实体
 * @author yibi
 * @date 2021-06-25 11:33
 */
@Data
public class UserAndChainResp {
    /**用户名称*/
    private String uname;
    /**用户链上地址*/
    private String publicKey;
    /**用户私钥*/
    private String privateKey;
    /**wesign用户id*/
    private String signUserId;
    /**合约名称*/
    private String contractName;
    /**合约地址*/
    private String contractAddress;
    /**合约部署时间*/
    private String createTimeStr;
}
