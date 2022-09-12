package com.example.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.entity.Student;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.mapper.StudentMapper;
import com.example.zhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author yl
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-09-01 18:05:48
*/
@Service
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Override
    public Student login(LoginForm loginForm) {
        return baseMapper.selectOne(new QueryWrapper<Student>()
                .eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword())));
    }

    @Override
    public Student getStudentById(Long userId) {
        return baseMapper.selectOne(new QueryWrapper<Student>().eq("id", userId));
    }

    @Override
    public IPage<Student> selectStudentByPage(Page<Student> page, String clazzName, String studentName) {
        QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.eq("clazz_name", clazzName);
        }
        if (!StringUtils.isEmpty(studentName)) {
            queryWrapper.like("name", studentName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}




