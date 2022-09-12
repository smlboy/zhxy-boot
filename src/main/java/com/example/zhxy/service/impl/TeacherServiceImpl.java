package com.example.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.entity.Teacher;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.mapper.TeacherMapper;
import com.example.zhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author yl
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-09-01 18:05:52
*/
@Service
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Override
    public Teacher login(LoginForm loginForm) {
        return baseMapper.selectOne(new QueryWrapper<Teacher>()
                .eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword())));
    }

    @Override
    public Teacher geTeacherById(Long userId) {
        return baseMapper.selectOne(new QueryWrapper<Teacher>().eq("id", userId));
    }

    @Override
    public IPage<Teacher> getTeachersByPage(Page<Teacher> page, String clazzName, String teacherName) {
        QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.eq("class_name", clazzName);
        }
        if (!StringUtils.isEmpty(teacherName)) {
            queryWrapper.like("name", teacherName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




