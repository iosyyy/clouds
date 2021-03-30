package com.jhx.payment2.service.imp;

import com.jhx.payment2.dao.CourseMapper;
import com.jhx.payment2.po.Course;
import com.jhx.payment2.service.CourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@Service
public class CourseServiceImp implements CourseService {
    @Resource
    CourseMapper courseMapper;

    @Override
    public Course selectByPrimaryKey(String courseId) {
        return courseMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public int insert(Course record) {
        return courseMapper.insert(record);
    }
}
