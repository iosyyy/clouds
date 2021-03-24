package com.jhx.coustomer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@SpringBootApplication
@EnableEurekaClient
public class CoustomerApplication {
    public static void main(String[] args) {

        SpringApplication.run(CoustomerApplication.class,args);
    }
}
