package com.yibi.evidence.chain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibi.evidence.chain.persist.entity.EviAttachEntity;
import com.yibi.evidence.chain.response.EviResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 存证附件表 服务类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
public interface EviAttachService extends IService<EviAttachEntity> {
    /**
     * 文件上传实现
     * @author yibi
     * @date 2021-06-28 15:54
     * @param multipartFile 上传文件
     * @return com.yibi.evidence.chain.response.EviResponse
     */
    EviResponse uploadFile(MultipartFile multipartFile);

    /**
     * 根据附件编号获取附件实体
     * @author yibi
     * @date 2021-06-28 17:09
     * @param fileNo 附件编号
     * @return com.yibi.evidence.chain.persist.entity.EviAttachEntity
     */
    EviAttachEntity getByFileNo(String fileNo);

    /**
     * 生成附件hash
     * @author yibi
     * @date 2021-06-28 17:13
     * @param attachPath 附件路径
     * @return java.lang.String
     */
    String generateFileHash(String attachPath);

    /**
     * 根据存证数据id查询附件列表
     * @author yibi
     * @date 2021-06-28 17:55
     * @param dataId 存证数据id
     * @return java.util.List<com.yibi.evidence.chain.persist.entity.EviAttachEntity>
     */
    List<EviAttachEntity> listByDataId(Integer dataId);
}
