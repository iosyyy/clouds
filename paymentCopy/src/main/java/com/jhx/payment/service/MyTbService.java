package com.jhx.payment.service;

import com.jhx.payment.po.MyTb;
import org.springframework.stereotype.Service;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@Service
public interface MyTbService {
    int insert(MyTb record);

    MyTb selectByPrimaryKey(Integer mId);
}
