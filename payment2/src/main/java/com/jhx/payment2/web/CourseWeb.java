package com.jhx.payment2.web;

import com.jhx.payment2.po.Course;
import com.jhx.payment2.po.ResultMap;
import com.jhx.payment2.service.CourseService;
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
public class CourseWeb {

    @Resource
    CourseService courseService;

    @PostMapping("/insertMyTb")
    public ResultMap InsertMyTb(Course course){
        log.info("insert MyTb start..."+course);
        int insert = courseService.insert(course);
        if (insert==0){
            log.error("insert"+course+" failed");
            return new ResultMap(0,"insert"+course+" failed",insert);
        }else{
            log.info("insert"+course+"success");
            return new ResultMap(500,"insert"+course+"success",insert);
        }
    }

    @GetMapping("/selectMyTb")
    public ResultMap SelectMyTb(String courseId){
        log.info("select MyTb start..."+courseId);
        Course myTb = courseService.selectByPrimaryKey(courseId);
        if (myTb==null){
            log.error("select"+courseId+" failed");
            return new ResultMap(0,"select"+courseId+" failed",myTb);
        }else{
            log.info("select"+courseId+"success");
            return new ResultMap(500,"select"+courseId+"success",myTb);
        }
    }
}
