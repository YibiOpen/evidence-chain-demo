package com.yibi.evidence.chain.enums;

/**
 * 产品|节点状态枚举
 * @author yibi
 * @date 2021-06-25 11:03
 */
public enum OpenStatusEnum {
    /**停用*/
    STOP(-1, "停用"),
    /**正常*/
    START(1, "正常");

    public final Integer code;
    public final String msg;

    OpenStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
