package com.yibi.evidence.chain.controller;

import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviAttachService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *存证文件控制器
 *@author mcw
 *@date 2021-06-28
 */
@RestController
@RequestMapping("eviChain/file")
public class EviFileController {

    private final EviAttachService attachService;

    public EviFileController(EviAttachService attachService) {
        this.attachService = attachService;
    }

    /**
     * 文件上传接口
     * @author yibi
     * @date 2021-06-28 15:45
     * @param multipartFile 上传文件
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    @PostMapping("upload")
    public EviResponse upload(@RequestParam("file") MultipartFile multipartFile) {
        return attachService.uploadFile(multipartFile);
    }

}
