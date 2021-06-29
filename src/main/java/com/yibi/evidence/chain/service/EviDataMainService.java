package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviDataMainEntity;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.vo.req.DataReq;
import com.yibi.evidence.chain.vo.req.DataSaveReq;

/**
 * <p>
 * 存证主数据表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviDataMainService extends IService<EviDataMainEntity> {
    /**
     * 获取存证数据信息分页列表
     * @author yibi
     * @date 2021-06-24 12:47
     * @param dataReq
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse dataList(DataReq dataReq);

    /**
     * 处理数据存证实现
     * @author yibi
     * @date 2021-06-24 12:47
     * @param dataSaveReq 存证数据
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse bqSave(DataSaveReq dataSaveReq);

    /**
     * 存证数据链上核验实现
     * @author yibi
     * @date 2021-06-25 11:27
     * @param dataId 存证数据id
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse validChain(Integer dataId);

    /**
     * 获取存证上链统计信息实现
     * @author yibi
     * @date 2021-06-25 16:07
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse getTotalInfo();

    /**
     * 存证数据预览实现
     * @author yibi
     * @date 2021-06-27 14:47
     * @param dataId 存证数据id
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse previewChain(Integer dataId);
}
