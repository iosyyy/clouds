package com.jhx.payment.web;

import com.jhx.payment.po.MyTb;
import com.jhx.payment.po.ResultMap;
import com.jhx.payment.service.MyTbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@RestController
@Slf4j
public class MyTbWeb {
    @Resource
    MyTbService myTbService;

    @PostMapping("/insertMyTb")
    public ResultMap InsertMyTb(MyTb myTb){
        log.info("insert MyTb start..."+myTb);
        int insert = myTbService.insert(myTb);
        if (insert==0){
            log.error("insert"+myTb+" failed");
            return new ResultMap(0,"insert"+myTb+" failed",insert);
        }else{
            log.info("insert"+myTb+"success");
            return new ResultMap(500,"insert"+myTb+"success",insert);
        }
    }

    @GetMapping("/selectMyTb")
    public ResultMap SelectMyTb(Integer mId){
        log.info("select MyTb start..."+mId);
        MyTb myTb = myTbService.selectByPrimaryKey(mId);
        if (myTb==null){
            log.error("select"+mId+" failed");
            return new ResultMap(0,"select"+mId+" failed",myTb);
        }else{
            log.info("select"+mId+"success");
            return new ResultMap(500,"select"+mId+"success",myTb);
        }
    }
}
