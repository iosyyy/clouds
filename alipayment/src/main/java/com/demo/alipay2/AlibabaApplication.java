package com.demo.alipay2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 靖鸿宣
 * @since 2021/4/14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AlibabaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlibabaApplication.class, args);
    }

}
