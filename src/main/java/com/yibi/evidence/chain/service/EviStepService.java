package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviStepEntity;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.vo.req.StepReq;

/**
 * <p>
 * 配置节点表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviStepService extends IService<EviStepEntity> {
    /**
     * 获取节点信息分页列表
     * @author yibi
     * @date 2021-06-24 12:41
     * @param stepReq 节点请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse stepList(StepReq stepReq);

    /**
     * 获取节点信息select查询列表
     * @author yibi
     * @date 2021-06-24 12:41
     * @param stepReq 节点请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse selectList(StepReq stepReq);

    /**
     * 通过节点id获取节点名称
     * @author yibi
     * @date 2021-06-26 20:32
     * @param stepId 节点id
     * @return java.lang.String
     */
    String getNameById(Integer stepId);

    /**
     * 添加节点信息
     * @author yibi
     * @date 2021-06-27 22:13
     * @param stepReq 节点添加请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse addStep(StepReq stepReq);

    /**
     * 修改节点信息
     * @author yibi
     * @date 2021-06-27 22:13
     * @param stepReq 节点修改请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse modifyStep(StepReq stepReq);
}
