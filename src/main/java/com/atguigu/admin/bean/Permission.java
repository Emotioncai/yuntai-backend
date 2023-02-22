package com.atguigu.admin.bean;

import lombok.Data;

import java.util.List;
@Data
public class Permission {
    private Long id;
    private Long parentId;
    private String permissionName;
    private String permissionCode;
    private Integer level;
    private List<Permission> children;
    private Boolean select = false;
}
