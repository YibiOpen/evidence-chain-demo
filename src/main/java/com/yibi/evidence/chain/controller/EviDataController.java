package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviDataMainService;
import com.yibi.evidence.chain.vo.req.DataReq;
import com.yibi.evidence.chain.vo.req.DataSaveReq;
import com.yibi.evidence.chain.vo.req.DataValidReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *存证数据管理控制器
 *@author yibi
 *@date 2021-06-24
 */
@RestController
@RequestMapping("eviChain/data")
public class EviDataController {

    private final EviDataMainService dataService;

    public EviDataController(EviDataMainService dataService) {
        this.dataService = dataService;
    }

    /**
     * 存证数据列表接口
     * @author yibi
     * @date 2021-06-25 10:51
     * @param dataReq 存证数据查询请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/list")
    public EviResponse list(@RequestBody DataReq dataReq) {
        return dataService.dataList(dataReq);
    }

    /**
     * 模拟数据存证接口
     * @author yibi
     * @date 2021-06-25 10:51
     * @param dataSaveReq 存证数据
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/bqSave")
    public EviResponse bqSave(@RequestBody DataSaveReq dataSaveReq) {
        return dataService.bqSave(dataSaveReq);
    }

    /**
     * 存证数据链上校验接口
     * @author yibi
     * @date 2021-06-25 11:26
     * @param validReq 存证校验请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/validChain")
    public EviResponse validChain(@RequestBody DataValidReq validReq) {
        return dataService.validChain(validReq.getDataId());
    }

    /**
     * 存证数据预览接口
     * @author yibi
     * @date 2021-06-25 11:26
     * @param validReq 存证校验请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/previewChain")
    public EviResponse previewChain(@RequestBody DataValidReq validReq) {
        return dataService.previewChain(validReq.getDataId());
    }

    /**
     * 获取存证上链统计信息接口
     * @author yibi
     * @date 2021-06-25 16:07
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/getTotalInfo")
    public EviResponse getTotalInfo() {
        return dataService.getTotalInfo();
    }
}
