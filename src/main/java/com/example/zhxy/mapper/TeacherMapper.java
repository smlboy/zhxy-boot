package com.example.zhxy.mapper;

import com.example.zhxy.entity.Teacher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yl
* @description 针对表【tb_teacher】的数据库操作Mapper
* @createDate 2022-09-01 18:05:52
* @Entity com.example.zhxy.entity.Teacher
*/
@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {

}




