package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author yl
* @description 针对表【tb_clazz】的数据库操作Service
* @createDate 2022-09-01 18:05:09
*/
public interface ClazzService extends IService<Clazz> {

    IPage<Clazz> selectClazzByPage(Page<Clazz> clazzPage, String clazzName, String gradeName);
}
