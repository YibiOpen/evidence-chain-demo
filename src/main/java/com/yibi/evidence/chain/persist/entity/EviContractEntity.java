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
 * 存证合约表
 * </p>
 *
 * @author yibi
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("evi_contract")
public class EviContractEntity extends Model<EviContractEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 合约地址
     */
    @TableField("contract_address")
    private String contractAddress;

    /**
     * 合约名称
     */
    @TableField("contract_name")
    private String contractName;

    /**
     * wesign的用户id
     */
    @TableField("sign_user_id")
    private String signUserId;

    /**
     * wesign用户公钥信息
     */
    @TableField("public_key")
    private String publicKey;

    /**
     * wesign用户私钥base64信息
     */
    @TableField("private_key")
    private String privateKey;

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
