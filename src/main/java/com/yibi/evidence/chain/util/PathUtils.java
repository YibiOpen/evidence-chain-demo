package com.yibi.evidence.chain.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 路径拼接工具类
 * @author yibi
 * @date 2021-06-25 12:45
 */
public class PathUtils {
    
    /**
     * 路径参数拼接
     * @author yibi
     * @date 2021-06-28 16:17
     * @param paths 多个路径参数
     * @return java.lang.String
     */
    public static String concat(String... paths) {
        StringBuilder stringBuilder = new StringBuilder(128);
        int pathLength = paths.length;
        for (int i = 0; i < pathLength; ++i) {
            String path = paths[i];
            if (StringUtils.isBlank(path)) {
                continue;
            }
            if (!path.startsWith("/") && !path.contains(":")) {
                stringBuilder.append("/");
            }
            stringBuilder.append(path);
            char last = stringBuilder.charAt(stringBuilder.length() - 1);
            if ("/".equals(String.valueOf(last))) {
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            }
        }
        return stringBuilder.toString();
    }
}
