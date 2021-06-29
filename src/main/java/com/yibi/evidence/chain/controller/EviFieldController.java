package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviFieldService;
import com.yibi.evidence.chain.vo.req.FieldAddReq;
import com.yibi.evidence.chain.vo.req.FieldModifyReq;
import com.yibi.evidence.chain.vo.req.FieldReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *存证要素管理控制器
 *@author yibi
 *@date 2021-06-24
 */
@RestController
@RequestMapping("eviChain/field")
public class EviFieldController {

    private final EviFieldService fieldService;

    public EviFieldController(EviFieldService fieldService) {
        this.fieldService = fieldService;
    }

    /**
     * 要素分页列表接口
     * @author yibi
     * @date 2021-06-27 22:53
     * @param fieldReq 要素请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/list")
    public EviResponse list(@RequestBody FieldReq fieldReq) {
        return fieldService.fieldList(fieldReq);
    }

    /**
     * 要素下拉选择框接口
     * @author yibi
     * @date 2021-06-27 22:53
     * @param fieldReq 要素请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/selectList")
    public EviResponse selectList(@RequestBody FieldReq fieldReq) {
        return fieldService.selectList(fieldReq.getStepId());
    }

    /**
     * 要素添加接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @param fieldAddReq 要素请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/add")
    public EviResponse add(@RequestBody FieldAddReq fieldAddReq) {
        return fieldService.addFields(fieldAddReq);
    }

    /**
     * 要素修改接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @param fieldModifyReq 要素请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/modify")
    public EviResponse modify(@RequestBody FieldModifyReq fieldModifyReq) {
        return fieldService.modifyField(fieldModifyReq);
    }
}
