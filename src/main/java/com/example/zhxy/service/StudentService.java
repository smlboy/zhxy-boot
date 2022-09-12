package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.entity.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yl
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-09-01 18:05:48
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);

    IPage<Student> selectStudentByPage(Page<Student> page, String clazzName, String studentName);
}
