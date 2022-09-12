package com.example.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.zhxy.entity.Admin;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.mapper.AdminMapper;
import com.example.zhxy.util.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author yl
 * @description 针对表【tb_admin】的数据库操作Service实现
 * @createDate 2022-09-01 18:03:57
 */
@Service
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
        implements AdminService {

    @Override
    public Admin login(LoginForm loginForm) {
        return baseMapper.selectOne(new QueryWrapper<Admin>()
                .eq("name", loginForm.getUsername())
                .eq("password", MD5.encrypt(loginForm.getPassword())));
    }

    @Override
    public Admin getAdminById(Long userId) {
        return baseMapper.selectOne(new QueryWrapper<Admin>().eq("id", userId));
    }

    @Override
    public IPage<Admin> getAllAdminByPage(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name", adminName);
        }
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}





