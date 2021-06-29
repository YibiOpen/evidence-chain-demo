package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviUserEntity;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.vo.req.LoginReq;

/**
 * <p>
 * 管理用户表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviUserService extends IService<EviUserEntity> {
    /**
     * 用户登录处理
     * @author yibi
     * @date 2021-06-24 10:36
     * @param loginReq 登录请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse login(LoginReq loginReq);

    /**
     * 部署存证合约
     * @author yibi
     * @date 2021-06-24 10:36
     * @param userId 登录用户id
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    EviResponse deployContract(Integer userId);

    /**
     * 获取链节点信息
     * @author yibi
     * @date 2021-06-28 10:48
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse chainInfo();

    /**
     * 获取用户和链信息
     * @author yibi
     * @date 2021-06-28 11:51
     * @param userId 用户id
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse getInfoAndChain(Integer userId);
}
