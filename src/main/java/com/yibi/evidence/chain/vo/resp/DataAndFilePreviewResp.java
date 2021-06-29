package com.yibi.evidence.chain.vo.resp;

import lombok.Data;

import java.util.List;

/**
 *存证数据和附件预览实体
 *@author mcw
 *@date 2021-06-28
 */
@Data
public class DataAndFilePreviewResp {
    /**存证数据列表*/
    private List<DataPreviewResp> dataPreviewRespList;
    /**存证附件列表*/
    private List<FilePreviewResp> filePreviewRespList;
}
