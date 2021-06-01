package com.demo.alipay2.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebTest {
    @Value("${server.port}")
    String port;

    @GetMapping("/hello")
    public String getHello() {
        return "nacos serverport:"+port;
    }
}