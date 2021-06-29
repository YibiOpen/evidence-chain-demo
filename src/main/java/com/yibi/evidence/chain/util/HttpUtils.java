package com.yibi.evidence.chain.util;

import java.util.Map;

import cn.hutool.http.HttpUtil;

/**
 * http请求工具类
 * @author yibi
 * @date 2021-06-25 12:45
 */
public class HttpUtils {

    private HttpUtils() {
        throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
    }

    /**
     * 设置超时时间60秒
     */
    public static final int TIMEOUT = 60 * 1000;


    /**
     * http get 参数传参
     * @param url
     * @param param
     * @return
     */
    public static String httpGet(String url, Map<String, Object> param) {
        return HttpUtil.get(url, param, TIMEOUT);
    }

    /**
     * http post + map
     * @param url
     * @param param
     * @return
     */
    public static String httpPost(String url, Map<String, Object> param) {
        return HttpUtil.post(url, param, TIMEOUT);
    }

    /**
     * http post + json
     * @param url
     * @param jsonStr
     * @return
     */
    public static String httpPostByJson(String url, String jsonStr) {
        return HttpUtil.post(url, jsonStr, TIMEOUT);
    }

}
