package com.gateway.demoTwo.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;

/**
 * @author 靖鸿宣
 * @since 2021/4/4
 */
@Configuration
public class TestIt {
    @Bean
    public ServerCodecConfigurer serverCodecConfigurer() {
        return ServerCodecConfigurer.create();
    }
}
