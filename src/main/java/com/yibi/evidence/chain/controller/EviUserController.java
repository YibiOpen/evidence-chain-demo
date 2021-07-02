package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviUserService;
import com.yibi.evidence.chain.vo.req.LoginReq;
import com.yibi.evidence.chain.vo.req.UserContractReq;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理控制器
 * @author yibi
 * @date 2021-06-24 10:25
 */
@RestController
@RequestMapping("eviChain/user")
public class EviUserController {

    private final EviUserService userService;

    public EviUserController(EviUserService userService) {
        this.userService = userService;
    }

    /**
     * 管理员登录
     * @author yibi
     * @date 2021-06-24 10:27
     * @param loginReq 登录请求
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/login")
    public EviResponse login(@RequestBody LoginReq loginReq) {
        return userService.login(loginReq);
    }

    /**
     * 获取链信息
     * @author yibi
     * @date 2021-06-24 10:27
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/chainInfo")
    public EviResponse chainInfo() {
        return userService.chainInfo();
    }

    /**
     * 部署存证合约接口
     * @author yibi
     * @date 2021-06-28 11:16
     * @param contractReq 合约部署请求
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("/deployContract")
    public EviResponse deployContract(@RequestBody UserContractReq contractReq) {
        return userService.deployContract(contractReq.getUserId());
    }

    /**
     * 获取用户信息
     * @author yibi
     * @date 2021-06-24 10:27
     * @param userId 用户id
     * @return com.yibi.evidence.chain.common.EviResponse
     */
    @PostMapping("/{userId}")
    public EviResponse userInfo(@PathVariable("userId") Integer userId) {
        return userService.getInfoAndChain(userId);
    }
}
