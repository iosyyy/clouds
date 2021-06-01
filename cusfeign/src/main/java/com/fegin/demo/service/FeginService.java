package com.fegin.demo.service;

import com.fegin.demo.service.Imp.FeginServiceImp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 靖鸿宣
 * @since 2021/4/1
 */
@FeignClient(value = "CLOUD-PAYMENT-SERVICE",fallback = FeginServiceImp.class)
@Service
public interface FeginService {
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String getTimeOut(@PathVariable("id") Integer id);
}
