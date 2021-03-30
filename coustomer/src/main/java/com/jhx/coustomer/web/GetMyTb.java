package com.jhx.coustomer.web;

import com.jhx.coustomer.po.MyTb;
import com.jhx.coustomer.po.ResultMap;
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
    public RestTemplate restTemplate;

    private String url="http://CLOUD-PAYMENT-SERVICE/";

    @GetMapping("/get")
    public ResultMap insertMyTb(MyTb myTb){
        return restTemplate.postForObject(url+"payment/insertMyTb",myTb,ResultMap.class);
    }
    @GetMapping("/select")
    public ResultMap selectMyTb(Integer mId){
        return restTemplate.getForObject(url+"payment/selectMyTb",ResultMap.class,mId);
    }
}
