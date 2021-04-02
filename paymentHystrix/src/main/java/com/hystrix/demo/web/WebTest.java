package com.hystrix.demo.web;

import com.hystrix.demo.service.TestServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/4/2
 */
@RestController
@Slf4j
public class WebTest {
    @Resource
    TestServices services;

    @GetMapping("/payment/hystrix/normal/{id}")
    public String getIt(@PathVariable("id") Integer id){
        log.info("start running");
        return services.getIt(id);
    }

    @GetMapping("/payment/hystrix/timeout/{id}")
    public String getTimeOut(@PathVariable("id") Integer id){
        String word=services.Timeout(id);
        log.info("start running");
        return word;
    }
}
