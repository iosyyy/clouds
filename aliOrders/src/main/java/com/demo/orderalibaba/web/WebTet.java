package com.demo.orderalibaba.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/4/14
 */
@RestController
public class WebTet {
    @Resource
    private RestTemplate restTemplate;

    String serviceUrl="http://nacos-provider";

    @GetMapping("/getIt")
    public String getIt(){

        return restTemplate.getForObject(serviceUrl+"/hello",String.class);
    }
}
