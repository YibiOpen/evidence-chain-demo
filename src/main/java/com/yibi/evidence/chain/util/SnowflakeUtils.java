package com.yibi.evidence.chain.util;

import cn.hutool.core.lang.Snowflake;

/**
 * Snowflake工具类
 *@author mcw
 *@date 2021-06-28
 */
public class SnowflakeUtils {

    /**
     * 生成Snowflake id
     * @author yibi
     * @date 2021-06-28 16:02
     * @return java.lang.String
     */
    public static String createNo() {
        Snowflake snowflake = new Snowflake(1, 1);
        return snowflake.nextIdStr();
    }
}
