package com.example.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.Clazz;
import com.example.zhxy.service.ClazzService;
import com.example.zhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
* @author yl
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-09-01 18:05:09
*/
@Service
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{

    @Override
    public IPage<Clazz> selectClazzByPage(Page<Clazz> clazzPage, String clazzName, String gradeName) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(clazzName)) {
            queryWrapper.like("name", clazzName);
        }
        if (!StringUtils.isEmpty(gradeName)) {
            queryWrapper.eq("grade_name", gradeName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(clazzPage,queryWrapper);
    }
}




