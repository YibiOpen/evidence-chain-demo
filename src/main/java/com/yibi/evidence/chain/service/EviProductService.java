package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviProductEntity;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.vo.req.ProductReq;

/**
 * <p>
 * 配置产品表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviProductService extends IService<EviProductEntity> {
    /**
     * 获取产品信息分页列表
     * @author yibi
     * @date 2021-06-24 12:18
     * @param productReq 产品请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse productList(ProductReq productReq);

    /**
     * 获取产品信息下拉框列表
     * @author yibi
     * @date 2021-06-24 12:18
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse selectList();

    /**
     * 通过产品id获取名称
     * @author yibi
     * @date 2021-06-26 20:32
     * @param productId 产品id
     * @return java.lang.String
     */
    String getNameById(Integer productId);

    /**
     * 添加产品信息
     * @author yibi
     * @date 2021-06-27 21:29
     * @param productReq 产品请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse addProduct(ProductReq productReq);

    /**
     * 修改产品信息
     * @author yibi
     * @date 2021-06-27 21:29
     * @param productReq 产品请求实体
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse modifyProduct(ProductReq productReq);
}
