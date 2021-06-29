package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

/**
 *存证数据附件预览实体
 *@author yibi
 *@date 2021-06-27
 */
@Data
public class FilePreviewResp {
    /**存证附件名称*/
    private String fileName;
    /**存证附件上链地址*/
    private String chainAddress;
}
