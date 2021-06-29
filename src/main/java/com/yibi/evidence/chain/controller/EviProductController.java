package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviProductService;
import com.yibi.evidence.chain.vo.req.ProductReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *存证产品管理控制器
 *@author yibi
 *@date 2021-06-24
 */
@RestController
@RequestMapping("eviChain/product")
public class EviProductController {

    private final EviProductService productService;

    public EviProductController(EviProductService productService) {
        this.productService = productService;
    }

    /**
     * 产品分页列表接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @param productReq 产品请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/list")
    public EviResponse list(@RequestBody ProductReq productReq) {
        return productService.productList(productReq);
    }

    /**
     * 产品下拉选择框接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/selectList")
    public EviResponse selectList() {
        return productService.selectList();
    }

    /**
     * 产品添加接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @param productReq 产品请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/add")
    public EviResponse add(@RequestBody ProductReq productReq) {
        return productService.addProduct(productReq);
    }

    /**
     * 产品修改接口
     * @author yibi
     * @date 2021-06-27 22:54
     * @param productReq 产品请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/modify")
    public EviResponse modify(@RequestBody ProductReq productReq) {
        return productService.modifyProduct(productReq);
    }
}
