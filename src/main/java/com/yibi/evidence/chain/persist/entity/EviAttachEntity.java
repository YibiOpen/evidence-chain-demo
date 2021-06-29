package com.yibi.evidence.chain.persist.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 存证附件表
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("evi_attach")
public class EviAttachEntity extends Model<EviAttachEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件编号
     */
    @TableField("file_no")
    private String fileNo;

    /**
     * 存证数据id
     */
    @TableField("data_id")
    private Integer dataId;

    /**
     * 附件名称
     */
    @TableField("attach_name")
    private String attachName;

    /**
     * 附件路径
     */
    @TableField("attach_path")
    private String attachPath;

    /**
     * 附件hash
     */
    @TableField("attach_hash")
    private String attachHash;

    /**
     * 附件hash算法
     */
    @TableField("hash_cal")
    private String hashCal;

    /**
     * 上链地址
     */
    @TableField("chain_status")
    private Integer chainStatus;

    /**
     * 附件上链地址
     */
    @TableField("chain_address")
    private String chainAddress;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
