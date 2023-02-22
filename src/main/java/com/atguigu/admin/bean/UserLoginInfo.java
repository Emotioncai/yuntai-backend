package com.atguigu.admin.bean;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class UserLoginInfo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户有权限访问的路由
     */
    private Set<String> routes = new HashSet<>();
}
