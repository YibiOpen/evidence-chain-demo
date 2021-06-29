package com.yibi.evidence.chain.vo.req;

import lombok.Data;

/**
 * 登录实体
 * @author yibi
 * @date 2021-06-24 10:28
 */
@Data
public class LoginReq {
    /**用户名*/
    private String username;
    /**用户密码*/
    private String encryptedPassword;
}
