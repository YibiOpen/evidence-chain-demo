package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviFieldEntity;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.vo.req.FieldAddReq;
import com.yibi.evidence.chain.vo.req.FieldModifyReq;
import com.yibi.evidence.chain.vo.req.FieldReq;
import com.yibi.evidence.chain.vo.resp.DataPreviewResp;

import java.util.List;

/**
 * <p>
 * 配置要素表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviFieldService extends IService<EviFieldEntity> {
    /**
     * 获取要素信息分页列表
     * @author yibi
     * @date 2021-06-24 12:44
     * @param fieldReq 要素请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse fieldList(FieldReq fieldReq);

    /**
     * 获取要素信息select查询列表
     * @author yibi
     * @date 2021-06-24 12:44
     * @param stepId 节点id
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse selectList(Integer stepId);

    /**
     * 预览存证证据时实现参数名转换
     * @author yibi
     * @date 2021-06-27 14:53
     * @param stepId 节点id
     * @param dataJson 存证json
     * @return java.util.List<com.yibi.evidence.chain.vo.resp.DataPreviewResp>
     */
    List<DataPreviewResp> previewFieldData(Integer stepId, String dataJson);

    /**
     * 要素批量添加
     * @author yibi
     * @date 2021-06-27 18:24
     * @param fieldAddReq 要素添加请求
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse addFields(FieldAddReq fieldAddReq);

    /**
     * 要素修改
     * @author yibi
     * @date 2021-06-27 20:04
     * @param fieldModifyReq 要素修改请求
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse modifyField(FieldModifyReq fieldModifyReq);
}
