package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zhxy.entity.Student;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.util.MD5;
import com.example.zhxy.util.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生数据 也可以模糊查询
     * @param pageNum
     * @param pageSize
     * @param clazzName
     * @param studentName
     * @return
     */
    @GetMapping("/getStudentByOpr/{pageNum}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNum")Integer pageNum,
                                  @PathVariable("pageSize")Integer pageSize,
                                  String clazzName,
                                  String studentName){
        //设置分页信息
        Page<Student> page = new Page<>(pageNum, pageSize);
        //通过分页查询
        IPage<Student> iPage=studentService.selectStudentByPage(page,clazzName,studentName);
        return Result.ok(iPage);
    }

    /**
     * 添加或者修改学生信息
     * @param student
     * @return
     */
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        //获取学生信息后 加密密码
       /* if (!StringUtils.isEmpty(student.getPassword())) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }*/

        //当前实体类对象没有id时代表添加 就需要对密码加密
        if(student.getId()==null||student.getId()==0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        //向数据库中保存信息
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    /**
     * 根据学生id删除或者批量删除
     * @param ids
     * @return
     */
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }
}
