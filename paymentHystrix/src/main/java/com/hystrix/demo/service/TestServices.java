package com.hystrix.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 靖鸿宣
 * @since 2021/4/2
 */
@Service
public class TestServices {
    public String getIt(Integer id){
        return "success"+Thread.currentThread().getName()+" "+id;
    }

    @HystrixCommand(fallbackMethod =  "FallBackFail",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })
    public String Timeout(Integer id){
        int time=5;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout"+id+" "+Thread.currentThread().getName();
    }

    public String FallBackFail(Integer id){
        return "running fail service callback fail"+" "+id;
    }
}
