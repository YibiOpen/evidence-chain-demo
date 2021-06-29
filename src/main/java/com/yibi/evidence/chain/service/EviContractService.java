package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviContractEntity;
import com.yibi.evidence.chain.persist.entity.EviUserEntity;

/**
 * <p>
 * 存证合约表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviContractService extends IService<EviContractEntity> {
    /**
     * 写入存证合约记录
     * @author yibi
     * @date 2021-06-24 17:49
     * @param contractName 合约名称
     * @param userEntity wesign用户
     * @param contractAddress 合约地址
     */
    void insertContract(String contractName, EviUserEntity userEntity, String contractAddress);

    /**
     * 数据上链
     * @author yibi
     * @date 2021-06-24 17:55
     * @param dataHash 上链数据hash
     * @param hashCal  上链数据hash算法
     * @param saveTime 上链时间
     * @return java.lang.String
     */
    String uploadChain(String dataHash, String hashCal, String saveTime);

    /**
     * 查询上链信息
     * @author yibi
     * @date 2021-06-25 9:23
     * @param dataAddress 数据上链地址
     * @return java.lang.String
     */
    String searchChain(String dataAddress);

    /**
     * 获取合约信息
     * @author yibi
     * @date 2021-06-28 10:50
     * @return com.yibi.evidence.chain.persist.entity.EviContractEntity
     */
    EviContractEntity getEviContract();
}
