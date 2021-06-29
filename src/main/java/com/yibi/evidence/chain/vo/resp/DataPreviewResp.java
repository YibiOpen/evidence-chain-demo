package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *存证数据预览实体
 *@author yibi
 *@date 2021-06-27
 */
@Data
public class DataPreviewResp {
    /**存证要素中文名*/
    private String chName;
    /**存证要素英文名*/
    private String enName;
    /**存证要素英文值*/
    private String enValue;

    public DataPreviewResp(String chName, String enName, String enValue) {
        this.chName = chName;
        this.enName = enName;
        this.enValue = enValue;
    }
}
