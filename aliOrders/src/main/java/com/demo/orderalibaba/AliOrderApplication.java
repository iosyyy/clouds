package com.demo.orderalibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 靖鸿宣
 * @since 2021/4/14
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AliOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliOrderApplication.class, args);
    }

}
