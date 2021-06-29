package com.yibi.evidence.chain.enums;

/**
 * 接口响应码枚举类
 * @author yibi
 * @date 2021-06-25 12:42
 */
public enum RespEnum {
    /**success*/
    SUCCESS(0, "success"),
    /**系统繁忙，请稍后再试*/
    UNKNOW_EXCEPTION(1000, "系统繁忙，请稍后再试"),
    /**参数格式校验失败*/
    VAILD_EXCEPTION(1001, "参数格式校验失败");

    private int code;
    private String msg;

    RespEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
