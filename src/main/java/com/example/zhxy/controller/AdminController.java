package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Admin;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 分页查询 模糊
     *
     * @param pageNum
     * @param pageSize
     * @param adminName
     * @return
     */
    @GetMapping("/getAllAdmin/{pageNum}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize,
                              String adminName) {
        Page<Admin> page = new Page<>(pageNum, pageSize);
        IPage<Admin> iPage = adminService.getAllAdminByPage(page, adminName);
        return Result.ok(iPage);
    }

    /**
     * 添加或者修改
     *
     * @param admin
     * @return
     */
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin) {

        /*
        这里会导致修改密码时把原来的密文密码重新加密
        if (!StringUtils.isEmpty(admin.getPassword())) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }*/
        //当前实体类对象没有id时代表添加 就需要对密码加密
        if (admin.getId() == null || admin.getId() == 0) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    /**
     * 删除一个或者多个
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
