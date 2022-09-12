package com.example.zhxy.mapper;

import com.example.zhxy.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author yl
* @description 针对表【tb_student】的数据库操作Mapper
* @createDate 2022-09-01 18:05:48
* @Entity com.example.zhxy.entity.Student
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}




