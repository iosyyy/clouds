package com.fegin.demo.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 靖鸿宣
 * @since 2021/4/2
 */
@Configuration
public class TestFeignLevel {
    @Bean
    public Logger.Level getLoggerLevel(){
        return Logger.Level.FULL;
    }
}
