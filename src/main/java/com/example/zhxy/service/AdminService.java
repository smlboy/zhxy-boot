package com.example.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zhxy.entity.LoginForm;

/**
* @author yl
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-09-01 18:03:57
*/
public interface AdminService extends IService<Admin> {

    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);

    IPage<Admin> getAllAdminByPage(Page<Admin> page, String adminName);
}
