package com.fegin.demo.service;

import com.fegin.demo.po.ResultMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 靖鸿宣
 * @since 2021/4/1
 */
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface FeginService {
    @GetMapping("/payment/selectMyTb")
    public ResultMap SelectMyTb(@RequestParam("mId") Integer mId);
}
