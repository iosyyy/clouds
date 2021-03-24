package com.jhx.euraka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@EnableEurekaServer
@SpringBootApplication
public class EurakaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurakaApplication.class,args);
    }
}
