package com.jhx.coustomer.web;

import com.jhx.coustomer.po.MyTb;
import com.jhx.coustomer.po.ResultMap;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@RestController
public class GetMyTb {
    @Resource
    public RestTemplateBuilder restTemplateBuilder;

    private String url="http://localhost:8080/payment/";

    @GetMapping("/get")
    public ResultMap InsertMyTb(MyTb myTb){
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.postForObject(url+"insertMyTb",myTb,ResultMap.class);
    }
    @GetMapping("/select")
    public ResultMap SelectMyTb(Integer mId){
        System.out.println(mId);
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate.getForObject(url+"selectMyTb",ResultMap.class,mId);
    }
}
