package com.atguigu.admin.bean;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String roleName;
}
