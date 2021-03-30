package com.jhx.payment2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@EnableEurekaClient
@SpringBootApplication
@MapperScan({"com.jhx.payment2.dao"})
public class Payment2Application {
    public static void main(String[] args) {
        SpringApplication.run(Payment2Application.class,args);
    }
}
