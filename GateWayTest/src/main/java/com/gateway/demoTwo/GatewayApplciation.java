package com.gateway.demoTwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author 靖鸿宣
 * @version 0
 * @since 2021/4/3
 */
@SpringBootApplication
@EnableEurekaServer
public class GatewayApplciation {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplciation.class, args);
    }

}
