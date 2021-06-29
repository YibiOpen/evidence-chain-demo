package com.yibi.evidence.chain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 区块链存证demo启动类
 * @author yibi
 * @date 2021-06-24 9:20
 */
@MapperScan("com.yibi.evidence.chain.persist.mapper")
@SpringBootApplication
public class EvidenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvidenceApplication.class, args);
    }

}
