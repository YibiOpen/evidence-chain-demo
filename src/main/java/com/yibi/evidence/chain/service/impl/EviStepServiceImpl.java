package com.yibi.evidence.chain.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.yibi.evidence.chain.enums.OpenStatusEnum;
import com.yibi.evidence.chain.persist.entity.EviStepEntity;
import com.yibi.evidence.chain.persist.mapper.IEviStepMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviProductService;
import com.yibi.evidence.chain.service.EviStepService;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.vo.req.StepReq;
import com.yibi.evidence.chain.vo.resp.EviStepResp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.yibi.evidence.chain.constant.EvidenceConstant.*;

/**
 * <p>
 * 配置节点表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviStepServiceImpl extends ServiceImpl<IEviStepMapper, EviStepEntity> implements EviStepService {

    @Resource
    private EviProductService productService;

    @Override
    public EviResponse stepList(StepReq stepReq) {
        LambdaQueryWrapper<EviStepEntity> wrapper = new LambdaQueryWrapper<>();
        if (null != stepReq.getProductId()) {
            wrapper.eq(EviStepEntity::getProductId, stepReq.getProductId());
        }
        if (StringUtils.isNotBlank(stepReq.getStepName())) {
            wrapper.eq(EviStepEntity::getStepName, stepReq.getStepName());
        }
        wrapper.orderByDesc(EviStepEntity::getId);
        IPage<EviStepEntity> iPage = new Page<>(stepReq.getCurrentPage(), stepReq.getPageSize());
        IPage<EviStepEntity> stepPage = this.page(iPage, wrapper);
        IPage<EviStepResp> stepRespPage = convertStepPage(stepPage);
        return EviResponse.ok(stepRespPage);
    }

    @Override
    public EviResponse selectList(StepReq stepReq) {
        LambdaQueryWrapper<EviStepEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviStepEntity::getProductId, stepReq.getProductId());
        List<EviStepEntity> stepList = this.list(wrapper);
        return EviResponse.ok(stepList);
    }

    @Override
    public String getNameById(Integer stepId) {
        return this.getById(stepId).getStepName();
    }

    @Override
    public EviResponse addStep(StepReq stepReq) {
        LambdaQueryWrapper<EviStepEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviStepEntity::getProductId, stepReq.getProductId());
        wrapper.eq(EviStepEntity::getStepName, stepReq.getStepName());
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("节点名称={}已存在,不能重复添加", stepReq.getStepName());
            return EviResponse.error("节点名称已存在");
        }
        String stepCode = createStepCode();
        EviStepEntity stepEntity = new EviStepEntity();
        stepEntity.setProductId(stepReq.getProductId());
        stepEntity.setStepName(stepReq.getStepName());
        stepEntity.setStepCode(stepCode);
        stepEntity.setOpenStatus(OpenStatusEnum.START.code);
        this.save(stepEntity);
        return EviResponse.ok();
    }

    @Override
    public EviResponse modifyStep(StepReq stepReq) {
        LambdaQueryWrapper<EviStepEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(EviStepEntity::getId, stepReq.getId());
        wrapper.eq(EviStepEntity::getStepName, stepReq.getStepName());
        int count = this.count(wrapper);
        if (count > 0) {
            log.warn("节点名称={}已存在,不能修改", stepReq.getStepName());
            return EviResponse.error("节点名称已存在");
        }
        EviStepEntity stepEntity = new EviStepEntity();
        stepEntity.setId(stepReq.getId());
        stepEntity.setStepName(stepReq.getStepName());
        this.updateById(stepEntity);
        return EviResponse.ok();
    }

    /**
     * 生成节点code
     * @author yibi
     * @date 2021-06-27 21:37
     * @return java.lang.String
     */
    private String createStepCode() {
        int count = this.count();
        return STEP_PREFIX + String.format(SUPPLY_ZERO + DIGIT, count + 1);
    }

    /**节点数据分页转换*/
    private IPage<EviStepResp> convertStepPage(IPage<EviStepEntity> stepPage) {
        IPage<EviStepResp> stepRespPage = new Page<>(stepPage.getCurrent(), stepPage.getSize());
        List<EviStepEntity> stepEntityList = stepPage.getRecords();
        List<EviStepResp> stepRespList = Lists.newArrayListWithCapacity(stepEntityList.size());
        stepEntityList.forEach((stepEntity) -> {
            EviStepResp stepResp = new EviStepResp();
            BeanUtils.copyProperties(stepEntity, stepResp);
            stepResp.setProductName(productService.getNameById(stepEntity.getProductId()));
            stepResp.setCreateTimeStr(DateUtils.formatDateTime(stepEntity.getCreateTime()));
            stepRespList.add(stepResp);
        });
        stepRespPage.setRecords(stepRespList);
        stepRespPage.setTotal(stepPage.getTotal());
        return stepRespPage;
    }
}
