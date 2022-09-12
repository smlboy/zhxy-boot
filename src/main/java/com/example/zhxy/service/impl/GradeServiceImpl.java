package com.example.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.Grade;
import com.example.zhxy.service.GradeService;
import com.example.zhxy.mapper.GradeMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author yl
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-09-01 18:05:44
*/
@Service
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

    @Override
    public IPage<Grade> getGradeByPage(Page<Grade> page, String gradeName) {
        //获取条件构造器
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        //判断当前是否执行模糊还是全部查询
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.like("name", gradeName);
        }
        //为条件构造器设置排序规则
        queryWrapper.orderByAsc("id");
        Page<Grade> gradePage = baseMapper.selectPage(page, queryWrapper);
        return page;
    }

    @Override
    public Object getAllGrades() {
        return baseMapper.selectList(null);
    }
}




