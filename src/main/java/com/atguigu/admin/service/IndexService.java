package com.atguigu.admin.service;

import com.atguigu.admin.bean.User;
import com.atguigu.admin.bean.UserLoginInfo;

import java.util.List;

public class IndexService {
    public static UserLoginInfo getUserInfo(String username) {
        User user = UserService.getUserByName(username);
        // 根据用户id获取权限列表
        if (user != null) {
            List<String> permissionByUserId = PermissionService.getPermissionByUserId(user.getId());
            UserLoginInfo userLoginInfo = new UserLoginInfo();
            userLoginInfo.setUsername(user.getUsername());
            userLoginInfo.getRoutes().addAll(permissionByUserId);
            return userLoginInfo;
        }

        return null;
    }
}
