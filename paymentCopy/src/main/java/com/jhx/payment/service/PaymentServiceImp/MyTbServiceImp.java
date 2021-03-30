package com.jhx.payment.service.PaymentServiceImp;

import com.jhx.payment.dao.MyTbMapper;
import com.jhx.payment.po.MyTb;
import com.jhx.payment.service.MyTbService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@Service
public class MyTbServiceImp implements MyTbService {
    @Resource
    MyTbMapper myTbMapper;

    @Override
    public int insert(MyTb record) {
        return myTbMapper.insert(record);
    }

    @Override
    public MyTb selectByPrimaryKey(Integer mId) {
        return myTbMapper.selectByPrimaryKey(mId);
    }
}
