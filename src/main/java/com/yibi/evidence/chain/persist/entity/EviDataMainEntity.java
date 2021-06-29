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
 * 存证主数据表
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("evi_data_main")
public class EviDataMainEntity extends Model<EviDataMainEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 产品id
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 环节id
     */
    @TableField("step_id")
    private Integer stepId;

    /**
     * 用户名称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 证件编号
     */
    @TableField("ident_no")
    private String identNo;

    /**
     * 证件类型
     */
    @TableField("ident_type")
    private String identType;

    /**
     * 存证数据
     */
    @TableField("data_json")
    private String dataJson;

    /**
     * 数据hash
     */
    @TableField("data_hash")
    private String dataHash;

    /**
     * hash算法
     */
    @TableField("hash_cal")
    private String hashCal;

    /**
     * 存证时间
     */
    @TableField("save_time")
    private LocalDateTime saveTime;

    /**
     * 上链地址
     */
    @TableField("chain_status")
    private Integer chainStatus;

    /**
     * 上链地址
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
