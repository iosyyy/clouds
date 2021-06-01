package com.config.client.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 靖鸿宣
 * @since 2021/4/8
 */
@RestController
@RefreshScope
public class WebTest {

    @Value("${config.info}")
    private String word;

    @GetMapping("/getit")
    public String getIt(){
        return word;
    }
}
