package com.example.zhxy.entity;

import lombok.Data;

/**
 * 表单信息
 */
@Data
public class LoginForm {
    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;
}
