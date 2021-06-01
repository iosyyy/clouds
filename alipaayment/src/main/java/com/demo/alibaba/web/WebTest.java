package com.demo.alibaba.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 靖鸿宣
 * @since 2021/4/14
 */
@RestController
public class WebTest {
    @Value("${server.port}")
    String port;

    @GetMapping("/hello")
    public String getHello() {
        return "nacos serverport:"+port;
    }
}
