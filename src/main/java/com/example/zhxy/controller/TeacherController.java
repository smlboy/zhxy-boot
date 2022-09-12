package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Teacher;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 分页查询 模糊
     * @param pageNum
     * @param pageSize
     * @param clazzName
     * @param teacherName
     * @return
     */
    @GetMapping("/getTeachers/{pageNum}/{pageSize}")
    public Result getTeachers(@PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize,
                              String clazzName,
                              String teacherName) {
        Page<Teacher> page = new Page<>(pageNum, pageSize);
        IPage<Teacher> iPage = teacherService.getTeachersByPage(page, clazzName, teacherName);
        return Result.ok(iPage);
    }

    /**
     * 添加或者修改
     * @param teacher
     * @return
     */
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher){
        /*if (!StringUtils.isEmpty(teacher.getPassword())) {
             teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }*/
        //当前实体类对象没有id时代表添加 就需要对密码加密
        if(teacher.getId()==null||teacher.getId()==0){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    /**
     * 删除一个或者多个
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids){
        teacherService.removeByIds(ids);
        return Result.ok();
    }
}
