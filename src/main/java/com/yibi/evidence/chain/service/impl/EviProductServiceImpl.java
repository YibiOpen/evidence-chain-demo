package com.yibi.evidence.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibi.evidence.chain.enums.OpenStatusEnum;
import com.yibi.evidence.chain.persist.entity.EviProductEntity;
import com.yibi.evidence.chain.persist.mapper.IEviProductMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviProductService;
import com.yibi.evidence.chain.vo.req.ProductReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yibi.evidence.chain.constant.EvidenceConstant.*;

/**
 * <p>
 * 配置产品表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviProductServiceImpl extends ServiceImpl<IEviProductMapper, EviProductEntity> implements EviProductService {

    @Override
    public EviResponse productList(ProductReq productReq) {
        LambdaQueryWrapper<EviProductEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(productReq.getProductName())) {
            wrapper.eq(EviProductEntity::getProductName, productReq.getProductName());
        }
        wrapper.orderByDesc(EviProductEntity::getId);
        IPage<EviProductEntity> iPage = new Page<>(productReq.getCurrentPage(), productReq.getPageSize());
        IPage<EviProductEntity> data = this.page(iPage, wrapper);
        return EviResponse.ok(data);
    }

    @Override
    public EviResponse selectList() {
        List<EviProductEntity> productList = this.list();
        return EviResponse.ok(productList);
    }

    @Override
    public String getNameById(Integer productId) {
        return this.getById(productId).getProductName();
    }

    @Override
    public EviResponse addProduct(ProductReq productReq) {
        LambdaQueryWrapper<EviProductEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviProductEntity::getProductName, productReq.getProductName());
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("产品名称={}已存在,不能重复添加", productReq.getProductName());
            return EviResponse.error("产品名称已存在");
        }
        String productCode = createProductCode();
        EviProductEntity productEntity = new EviProductEntity();
        productEntity.setProductName(productReq.getProductName());
        productEntity.setProductCode(productCode);
        productEntity.setOpenStatus(OpenStatusEnum.START.code);
        this.save(productEntity);
        return EviResponse.ok();
    }

    @Override
    public EviResponse modifyProduct(ProductReq productReq) {
        LambdaQueryWrapper<EviProductEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(EviProductEntity::getId, productReq.getId());
        wrapper.eq(EviProductEntity::getProductName, productReq.getProductName());
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("产品名称={}已存在,不能修改", productReq.getProductName());
            return EviResponse.error("产品名称已存在");
        }
        EviProductEntity productEntity = new EviProductEntity();
        productEntity.setId(productReq.getId());
        productEntity.setProductName(productReq.getProductName());
        this.updateById(productEntity);
        return EviResponse.ok();
    }

    /**
     * 生成产品code
     * @author yibi
     * @date 2021-06-27 21:37
     * @return java.lang.String
     */
    private String createProductCode() {
        int count = this.count();
        return PRODUCT_PREFIX + String.format(SUPPLY_ZERO + DIGIT, count + 1);
    }
}
