package com.demo.payment4.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 靖鸿宣
 * @since 2021/3/30
 */
@RestController
@Slf4j
public class WebCon {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/paymentps/consul")
    public String getIt(){
        log.info("running success");
        return "get server port:"+port+ UUID.randomUUID().toString();
    }
}
