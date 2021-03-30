package com.jhx.payment.dao;

import com.jhx.payment.po.MyTb;

public interface MyTbMapper {
    int deleteByPrimaryKey(Integer mId);

    int insert(MyTb record);

    int insertSelective(MyTb record);

    MyTb selectByPrimaryKey(Integer mId);

    int updateByPrimaryKeySelective(MyTb record);

    int updateByPrimaryKey(MyTb record);
}