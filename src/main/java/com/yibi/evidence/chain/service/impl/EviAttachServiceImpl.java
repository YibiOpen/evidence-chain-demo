package com.yibi.evidence.chain.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibi.evidence.chain.constant.EvidenceConstant;
import com.yibi.evidence.chain.persist.entity.EviAttachEntity;
import com.yibi.evidence.chain.persist.mapper.IEviAttachMapper;
import com.yibi.evidence.chain.response.EviResponse;
import com.yibi.evidence.chain.service.EviAttachService;
import com.yibi.evidence.chain.util.DateUtils;
import com.yibi.evidence.chain.util.PathUtils;
import com.yibi.evidence.chain.util.SnowflakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 存证附件表 服务实现类
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Slf4j
@Service
public class EviAttachServiceImpl extends ServiceImpl<IEviAttachMapper, EviAttachEntity> implements EviAttachService {

    @Value("${store.dir:null}")
    private String storeDir;

    @Override
    public EviResponse uploadFile(MultipartFile multipartFile) {
        String attachName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileNo = SnowflakeUtils.createNo();
            String attachPath = createAttachPath(attachName, fileNo);
            String fullPath = createFullPath(storeDir, attachPath);
            FileUtil.mkParentDirs(fullPath);
            FileUtil.writeFromStream(inputStream, fullPath);
            EviAttachEntity eviAttachEntity = new EviAttachEntity();
            eviAttachEntity.setFileNo(fileNo);
            eviAttachEntity.setAttachName(attachName);
            eviAttachEntity.setAttachPath(attachPath);
            this.save(eviAttachEntity);
            return EviResponse.ok(fileNo);
        } catch (IOException e) {
            log.warn("上传附件处理出现异常,附件名称={}", attachName, e);
            return EviResponse.error("上传附件处理失败");
        }
    }

    @Override
    public EviAttachEntity getByFileNo(String fileNo) {
        LambdaQueryWrapper<EviAttachEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviAttachEntity::getFileNo, fileNo);
        return this.getOne(wrapper);
    }

    @Override
    public String generateFileHash(String attachPath) {
        String fullPath = createFullPath(storeDir, attachPath);
        try (FileInputStream fis = new FileInputStream(fullPath)) {
            return DigestUtil.sha256Hex(fis);
        } catch (FileNotFoundException e) {
            log.warn("存证附件不存在,fullPath={}", fullPath, e);
        } catch (IOException e) {
            log.warn("计算存证附件hash出现IO异常,fullPath={}", fullPath, e);
        }
        return null;
    }

    @Override
    public List<EviAttachEntity> listByDataId(Integer dataId) {
        LambdaQueryWrapper<EviAttachEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EviAttachEntity::getDataId, dataId);
        return this.list(wrapper);
    }

    /**
     * 拼接附件全路径
     * @author yibi
     * @date 2021-06-28 16:19
     * @param storeDir 附件根目录
     * @param attachPath 附件子目录
     * @return java.lang.String
     */
    private String createFullPath(String storeDir, String attachPath) {
        if (StringUtils.isBlank(storeDir)) {
            storeDir = System.getProperty("user.home");
        }
        return PathUtils.concat(storeDir, attachPath);
    }

    /**
     * 创建附件路径
     * @author yibi
     * @date 2021-06-28 16:05
     * @param attachName 附件名称
     * @param fileNo 附件编号
     * @return java.lang.String
     */
    private String createAttachPath(String attachName, String fileNo) {
        String extName = FileUtil.extName(attachName);
        String fileName = fileNo + EvidenceConstant.DOT + extName;
        String pathPrefix = DateUtils.dateTimeNow(DateUtils.PATH_FORMAT);
        return PathUtils.concat(pathPrefix, fileName);
    }
}
