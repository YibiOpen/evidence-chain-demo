package com.yibi.evidence.chain.bo;

/**
 * 区块链交易返回log
 * @author yibi
 * @date 2021-06-25 12:37
 */
public class LogAddressBO {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "LogAddressBO{" +
                "address='" + address + '\'' +
                '}';
    }
}
