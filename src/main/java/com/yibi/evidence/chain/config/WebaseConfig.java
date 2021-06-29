package com.yibi.evidence.chain.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * webase配置类
 *@author yibi
 *@date 2021-06-24
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "webase.node.mgr")
public class WebaseConfig {
    /**webase接口地址*/
    private String url;
    /**webase接口key*/
    private String appKey;
    /**webase接口秘钥*/
    private String appSecret;
    /**webase接口是否加密传输*/
    private boolean transferEncrypt;
    /**webase应用外链url*/
    private String linkUrl;
    /**webase接入群组id*/
    private Integer groupId;
}
