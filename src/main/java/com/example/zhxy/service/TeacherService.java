package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yl
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-09-01 18:05:52
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher geTeacherById(Long userId);

    IPage<Teacher> getTeachersByPage(Page<Teacher> page, String clazzName, String teacherName);
}
