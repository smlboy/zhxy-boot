package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Grade;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yl
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-09-01 18:05:44
*/
public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByPage(Page<Grade> page, String gradeName);

    Object getAllGrades();
}
