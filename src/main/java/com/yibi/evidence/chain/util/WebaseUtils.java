package com.yibi.evidence.chain.util;

import com.yibi.evidence.chain.constant.WebaseConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.utils.Numeric;

import java.math.BigInteger;

/**
 * webase工具类
 * @author yibi
 * @date 2021-06-24 19:30
 */
@Slf4j
public class WebaseUtils {
    public static void main(String[] args) {
        String dataHash = "99542e08a977ee3ec037f0e319f8abe3";
        String privateKey = "ODYwZjJhNjczMzQ3ZTYwN2RkNDRhM2E0MWJlYzJmNDlmZWM2YmY0YmNkNjEyNGFiNDZiM2U2YmZmZjM3NjRjYQ==";
        Sign.SignatureData signatureData = signMessage(dataHash, privateKey);
        System.out.println(signatureData.getV());
        System.out.println(bytesToHexString(signatureData.getR()));
        System.out.println(bytesToHexString(signatureData.getS()));
    }


    /**
     * 通过用户私钥对数据hash进行签名
     * @author yibi
     * @date 2021-06-24 20:13
     * @param evidenceHash 数据hash
     * @param privateKey 用户私钥
     * @return org.fisco.bcos.web3j.crypto.Sign.SignatureData
     */
    public static Sign.SignatureData signMessage(String evidenceHash, String privateKey) {
        try {
            BigInteger privateInt = getBigIntegerFromBase64(privateKey);
            String inputPrivateKey = privateInt.toString(10);
            Credentials credentials = GenCredential.create(new BigInteger(inputPrivateKey).toString(16));
            return Sign.getSignInterface().signMessage(evidenceHash.getBytes(),
                    credentials.getEcKeyPair());
        } catch (RuntimeException e) {
            log.error("signMessage message:{} Exception:{}", evidenceHash, e);
            return null;
        }
    }

    /**转16进制*/
    public static String bytesToHexString(byte[] bArray) {
        StringBuffer sbuffer = new StringBuffer(bArray.length);
        sbuffer.append(WebaseConstant.ADDRESS_PREFIX);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2) {
                sbuffer.append(0);
            }
            sbuffer.append(sTemp);
        }
        return sbuffer.toString();
    }

    /**base64解码*/
    private static BigInteger getBigIntegerFromBase64(String base64) {
        return Numeric.toBigInt(new String(Base64.decodeBase64(base64)));
    }

    static public Sign.SignatureData stringToSignatureData(String signatureData) {
        byte[] byte3 = Numeric.hexStringToByteArray(signatureData);
        byte[] signR = new byte[32];
        System.arraycopy(byte3, 1, signR, 0, signR.length);
        byte[] signS = new byte[32];
        System.arraycopy(byte3, 1 + signR.length, signS, 0, signS.length);
        return new Sign.SignatureData(byte3[0], signR, signS);
    }

    static public String signatureDataToString(Sign.SignatureData signatureData) {
        byte[] byte3 = new byte[1 + signatureData.getR().length + signatureData.getS().length];
        byte3[0] = signatureData.getV();
        System.arraycopy(signatureData.getR(), 0, byte3, 1, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte3, signatureData.getR().length + 1, signatureData.getS().length);
        return Numeric.toHexString(byte3, 0, byte3.length, false);
    }
}
