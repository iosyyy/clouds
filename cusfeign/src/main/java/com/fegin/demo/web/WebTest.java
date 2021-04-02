package com.fegin.demo.web;

import com.fegin.demo.po.ResultMap;
import com.fegin.demo.service.FeginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/4/1
 */
@RestController
@Slf4j
public class WebTest {
    @Resource
    FeginService service;

    @GetMapping("/cou/selectMyTb")
    public ResultMap SelectMyTb(@RequestParam("mId") Integer mId){
        return service.SelectMyTb(mId);
    }
}
