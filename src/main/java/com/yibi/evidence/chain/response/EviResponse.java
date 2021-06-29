package com.yibi.evidence.chain.response;

import cn.hutool.http.HttpStatus;
import com.yibi.evidence.chain.enums.RespEnum;

import java.util.HashMap;

/**
 * 接口响应数据封装实体
 * @author yibi
 * @date 2021-06-25 12:39
 */
public class EviResponse extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public EviResponse() {
        put("code", RespEnum.SUCCESS.getCode());
        put("msg", RespEnum.SUCCESS.getMsg());
    }

    public static EviResponse error() {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, "未知异常，请联系管理员");
    }

    public static EviResponse error(String msg) {
        return error(HttpStatus.HTTP_INTERNAL_ERROR, msg);
    }

    public static EviResponse error(int code, String msg) {
        EviResponse eviResponse = new EviResponse();
        eviResponse.put("code", code);
        eviResponse.put("msg", msg);
        return eviResponse;
    }

    public static EviResponse error(RespEnum respEnum) {
        EviResponse eviResponse = new EviResponse();
        eviResponse.put("code", respEnum.getCode());
        eviResponse.put("msg", respEnum.getMsg());
        return eviResponse;
    }

    public static EviResponse ok() {
        return new EviResponse();
    }

    public EviResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static EviResponse ok(Object obj) {
        EviResponse eviResponse = new EviResponse();
        eviResponse.put("code", RespEnum.SUCCESS.getCode());
        eviResponse.put("msg", RespEnum.SUCCESS.getMsg());
        eviResponse.put("data", obj);
        return eviResponse;
    }

    public static Boolean isOk(EviResponse eviResponse) {
        Integer code = eviResponse.get("code") == null ? null : Integer.valueOf(eviResponse.get("code").toString());
        return code == RespEnum.SUCCESS.getCode();
    }
}
