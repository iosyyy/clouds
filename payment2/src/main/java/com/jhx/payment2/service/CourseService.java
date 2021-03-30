package com.jhx.payment2.service;

import com.jhx.payment2.po.Course;
import org.springframework.stereotype.Service;

/**
 * @author 靖鸿宣
 * @since 2021/3/24
 */
@Service
public interface CourseService {
    Course selectByPrimaryKey(String courseId);

    int insert(Course record);
}
