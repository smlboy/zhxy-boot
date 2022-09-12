package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Grade;
import com.example.zhxy.service.GradeService;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 查询年级信息 也可以模糊查询
     *
     * @param pageNum
     * @param pageSize
     * @param gradeName
     * @return
     */
    @GetMapping("/getGrades/{pageNum}/{pageSize}")
    public Result getGrades(@PathVariable("pageNum") Integer pageNum,
                            @PathVariable("pageSize") Integer pageSize,
                            String gradeName) {
        //设置分页对象
        Page<Grade> page = new Page<>(pageNum, pageSize);
        //通过分页查询数据
        IPage<Grade> iPage = gradeService.getGradeByPage(page, gradeName);
        //返回json数据
        return Result.ok(iPage);
    }

    /**
     * 添加或者修改，根据返回是否有id区别
     * 获取的是请求头的实体类对象数据
     *
     * @param grade
     * @return
     */
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade) {
        //调用saveOrUpdate
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 删除或者批量删除
     * 根据id的数量决定
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids) {
        //获取参数后 调用removeByIds
        gradeService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 查询所有年级信息 用于班级管理中年级名称查询的回显
     * @return
     */
    @GetMapping("/getGrades")
    public Result getGrades() {
        return Result.ok(gradeService.getAllGrades());
    }
}
