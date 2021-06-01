package com.demo.config.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 靖鸿宣
 * @since 2021/4/15
 */
@RestController
@Slf4j
@RefreshScope
public class WebTest {
    @Value("${config.info}")
    String info;

    @GetMapping("/getInfo")
    public String getInfo(){
        return info;
    }

}
