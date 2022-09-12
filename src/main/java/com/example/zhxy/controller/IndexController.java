package com.example.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zhxy.entity.Admin;
import com.example.zhxy.entity.LoginForm;
import com.example.zhxy.entity.Student;
import com.example.zhxy.entity.Teacher;
import com.example.zhxy.service.AdminService;
import com.example.zhxy.service.StudentService;
import com.example.zhxy.service.TeacherService;
import com.example.zhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class IndexController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 登录页面的验证码刷新
     *
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response) {
        //获取验证码图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码 返回字符串
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码放入session域对象中
        request.getSession().setAttribute("verifiCode", verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 登录验证
     *
     * @param loginForm
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, //将请求体转换为一个java对象
                        HttpServletRequest request) {
        //比较验证码
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginFormVerifiCode = loginForm.getVerifiCode();
        //避免当前验证码为空或者时间过久失效
        if (StringUtils.isEmpty(sessionVerifiCode)) {
            return Result.fail().message("验证码不能为空或者验证码已失效，请重新刷新验证码后重试！");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginFormVerifiCode)) {
            return Result.fail().message("验证码错误，请重试！");
        }
        //移除原有请求域中的验证码
        session.removeAttribute("verifiCode");

        //分用户类型验证
        Integer userType = loginForm.getUserType();
        Map<String, Object> map = new LinkedHashMap<>();
        if (userType.equals(1)) {
            try {
                Admin admin = adminService.login(loginForm);
                //调用JwtHelper的createToken方法将用户的信息转换为一个token密文相应给浏览器
                if (null != admin) {
                    String token = JwtHelper.createToken(admin.getId().longValue(), userType);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或者密码错误");
                }
                return Result.ok(map);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
        } else if (userType.equals(2)) {
            try {
                Student student = studentService.login(loginForm);
                //调用JwtHelper的createToken方法将用户的信息转换为一个token密文相应给浏览器
                if (null != student) {
                    String token = JwtHelper.createToken(student.getId().longValue(), userType);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或者密码错误");
                }
                return Result.ok(map);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
        } else if (userType.equals(3)) {
            try {
                Teacher teacher = teacherService.login(loginForm);
                //调用JwtHelper的createToken方法将用户的信息转换为一个token密文相应给浏览器
                if (null != teacher) {
                    String token = JwtHelper.createToken(teacher.getId().longValue(), userType);
                    map.put("token", token);
                } else {
                    throw new RuntimeException("用户名或者密码错误");
                }
                return Result.ok(map);
            } catch (RuntimeException e) {
                e.printStackTrace();
                return Result.fail().message(e.getMessage());
            }
        }
        return Result.fail().message("未查询到用户");
    }

    /**
     * 登录成功后跳转到index页面
     *
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfo(@RequestHeader("token") String token) {
        //检查token是否有效
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //解析token获取用户信息
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        //存放Result中的data字段 以键值对方式
        Map<String, Object> map = new LinkedHashMap<>();
        if (userType.equals(1)) {
            Admin admin = adminService.getAdminById(userId);
            map.put("userType", userType);
            map.put("user", admin);
        } else if (userType.equals(2)) {
            Student student = studentService.getStudentById(userId);
            map.put("userType", userType);
            map.put("user", student);
        } else if (userType.equals(3)) {
            Teacher teacher = teacherService.geTeacherById(userId);
            map.put("userType", userType);
            map.put("user", teacher);
        }
        return Result.ok(map);
    }

    /**
     * 实现文件上传
     *
     * @param multipartFile
     * @return
     */
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile) {
        //解决文件重名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        String newFileName = uuid.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));
        //确定保存的路径
        String portraitPath = "D:/javaLearning/zhxy/zhxy-boot/target/classes/public/upload/".concat(newFileName);
        //文件保存
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回浏览器要去的文件的位置
        return Result.ok("upload/".concat(newFileName));
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param token
     * @return
     */
    @PostMapping("/updatePwd/{oldPassword}/{newPassword}")
    public Result updatePassword(@PathVariable("oldPassword") String oldPassword,
                                 @PathVariable("newPassword") String newPassword,
                                 @RequestHeader String token) {
        //验证token是否失效
        if (JwtHelper.isExpiration(token)) {
            return Result.fail().message("登录信息已失效");
        }
        //解析token获取用户信息
        Integer userType = JwtHelper.getUserType(token);
        Integer userId = JwtHelper.getUserId(token).intValue();
        //将新旧密码转为暗文
        String oldPsw = MD5.encrypt(oldPassword);
        String newPsw = MD5.encrypt(newPassword);
        //判断用户类型
        if (userType.equals(1)) {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("password", oldPsw).eq("id", userId);
            Admin admin = adminService.getOne(queryWrapper);
            if (null != admin) {
                admin.setPassword(newPsw);
                adminService.updateById(admin);
            } else {
                return Result.fail().message("原密码错误");
            }
        } else if (userType.equals(2)) {
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("password", oldPsw).eq("id", userId);
            Student student = studentService.getOne(queryWrapper);
            if (null != student) {
                student.setPassword(newPsw);
                studentService.updateById(student);
            } else {
                return Result.fail().message("原密码错误");
            }
        } else if (userType.equals(3)) {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("password", oldPsw).eq("id", userId);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (null != teacher) {
                teacher.setPassword(newPsw);
                teacherService.updateById(teacher);
            } else {
                return Result.fail().message("原密码错误");
            }
        }
        return Result.ok();
    }
}
