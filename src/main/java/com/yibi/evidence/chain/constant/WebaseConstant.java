package com.yibi.evidence.chain.constant;

/**
 *webase相关常量类
 *@author yibi
 *@date 2021-06-24
 */
public class WebaseConstant {
    /**webase账号*/
    public static final String ACCOUNT = "admin";
    /**合约版本号*/
    public static final String CONTRACT_VERSION = "1.0.0";
    /**电子存证数据合约名*/
    public static final String EVIDENCE_CONTRACT_NAME = "Evidence";
    /**电子存证签名合约名*/
    public static final String EVIDENCE_CONTRACT_DATA_NAME = "EvidenceSignersData";

    public static final String SIGN_PUBLIC_KEY1 = "0xf3f7760df0a7c9902f1e1399dccc4b2116566a6e";

    public static final String SIGN_PUBLIC_KEY2 = "0x6bc952a2e4db9c0c86a368d83e9df0c6ab481102";
    /**查询存证上链数据的已编码字符串*/
    public static final String GET_EVIDENCE_ENCODE = "0x4ae70cef";
    /**地址前缀*/
    public static final String ADDRESS_PREFIX = "0x";
    /**区块链国密模式*/
    public static final Integer SM_ENCRYPT_TYPE = 1;
}
