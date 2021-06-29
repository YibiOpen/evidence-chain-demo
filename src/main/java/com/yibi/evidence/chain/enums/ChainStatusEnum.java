package com.yibi.evidence.chain.enums;

/**
 * 上链状态枚举
 * @author yibi
 * @date 2021-06-25 11:03
 */
public enum ChainStatusEnum {
    /**未上链*/
    NO(0, "未上链"),
    /**已上链*/
    YES(1, "已上链");

    public final Integer code;
    public final String msg;

    ChainStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
