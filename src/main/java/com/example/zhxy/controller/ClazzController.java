package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Clazz;
import com.example.zhxy.service.ClazzService;
import com.example.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 分页查询 也可以模糊查询
     * @param pageNum
     * @param pageSize
     * @param clazzName
     * @param gradeName
     * @return
     */
    @GetMapping("/getClazzsByOpr/{pageNum}/{pageSize}")
    public Result getClazzsByOpr(@PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("pageSize") Integer pageSize,
                                 String clazzName,
                                 String gradeName) {
        //设置如何分页
        Page<Clazz> clazzPage = new Page<>(pageNum, pageSize);
        //通过分页查询数据
        IPage<Clazz> iPage=clazzService.selectClazzByPage(clazzPage,clazzName,gradeName);
        return Result.ok(iPage);
    }

    /**
     * 修改或者保存班级信息
     * @param clazz
     * @return
     */
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    /**
     * 删除班级信息
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 用于学生管理中 班级条件查询的回显
     * @return
     */
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> list = clazzService.list();
        return Result.ok(list);
    }
}
