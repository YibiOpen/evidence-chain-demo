package com.yibi.evidence.chain.vo.req;

import lombok.Data;

import java.util.List;

/**
 *数据存证请求
 *@author yibi
 *@date 2021-06-24
 */
@Data
public class DataSaveReq {
    /**产品id*/
    private Integer productId;
    /**节点id*/
    private Integer stepId;
    /**用户名称*/
    private String userName;
    /**用户证件类型*/
    private String identType;
    /**用户证件号*/
    private String identNo;
    /**存证数据*/
    private List<DataItemReq> itemList;
    /**存证附件数据*/
    private List<DataFileReq> addedFileList;
}
