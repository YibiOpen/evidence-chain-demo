package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviStepService;
import com.yibi.evidence.chain.vo.req.StepReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *存证节点管理控制器
 *@author yibi
 *@date 2021-06-24
 */
@RestController
@RequestMapping("eviChain/step")
public class EviStepController {

    private final EviStepService stepService;

    public EviStepController(EviStepService stepService) {
        this.stepService = stepService;
    }

    /**
     * 节点分页列表接口
     * @author yibi
     * @date 2021-06-27 22:56
     * @param stepReq 节点请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/list")
    public EviResponse list(@RequestBody StepReq stepReq) {
        return stepService.stepList(stepReq);
    }

    /**
     * 节点下拉选择框接口
     * @author yibi
     * @date 2021-06-27 22:56
     * @param stepReq 节点请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/selectList")
    public EviResponse selectList(@RequestBody StepReq stepReq) {
        return stepService.selectList(stepReq);
    }

    /**
     * 节点添加接口
     * @author yibi
     * @date 2021-06-27 22:56
     * @param stepReq 节点请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/add")
    public EviResponse add(@RequestBody StepReq stepReq) {
        return stepService.addStep(stepReq);
    }

    /**
     * 节点添加接口
     * @author yibi
     * @date 2021-06-27 22:56
     * @param stepReq 节点请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/modify")
    public EviResponse modify(@RequestBody StepReq stepReq) {
        return stepService.modifyStep(stepReq);
    }
}
