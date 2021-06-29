package com.yibi.evidence.chain.enums;


/**
 * hash算法枚举值
 * @author yibi
 * @date 2021-06-24 17:56
 */
public enum HashCalEnum {
    /**MD5*/
    MD5("MD5"),
    /**SHA-256*/
    SHA256("SHA-256");

    public final String type;

    HashCalEnum(String type) {
        this.type = type;
    }
}
