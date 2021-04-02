package com.jhx.coustomer;

import com.jhx.test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = test.class)
public class CoustomerApplication {
    public static void main(String[] args) {

        SpringApplication.run(CoustomerApplication.class,args);
    }
}
