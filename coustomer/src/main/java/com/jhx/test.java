package com.jhx;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 靖鸿宣
 * @since 2021/4/1
 */
@Configuration
public class test {
 @Bean
 public IRule myRule(){
     return new RandomRule();
 }
}
