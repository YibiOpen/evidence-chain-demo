package com.yibi.evidence.chain.util;

import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 *url工具类
 *@author yibi
 *@date 2021-06-24
 */
@Slf4j
public class UrlUtils {
    /**
     * 解析url链接中的ip和端口（http://xx.xxx.xx.xx:xxxx/xx）
     * @author yibi
     * @date 2021-06-24 14:34
     * @param linkUrl url链接
     * @return cn.hutool.core.lang.Pair<java.lang.String, java.lang.Integer>
     */
    public static Pair<String, Integer> getHostAndPort(String linkUrl) {
        if (StringUtils.isBlank(linkUrl)) {
            return null;
        }
        try {
            URL url = new URL(linkUrl);
            String host = url.getHost();
            Integer port = url.getPort();
            return new Pair<>(host, port);
        } catch (MalformedURLException e) {
            log.error("url链接={}错误", linkUrl);
        }
        return null;
    }
}
