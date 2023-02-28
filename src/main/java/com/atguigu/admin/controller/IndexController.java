package com.atguigu.admin.controller;

import com.atguigu.admin.bean.User;
import com.atguigu.admin.bean.UserLoginInfo;
import com.atguigu.admin.service.IndexService;
import com.atguigu.admin.service.UserService;
import com.atguigu.admin.util.JwtHelper;
import com.atguigu.admin.util.MD5;
import com.atguigu.util.Result;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin/index")
public class IndexController {
    /**
     * 登录API
     */
    @PostMapping("login")
    public Result<String> login(@RequestBody User userInfo) {
        System.out.println(userInfo);
        User user = UserService.getUserByName(userInfo.getUsername());
        if (user != null) {
            if (!MD5.encrypt(userInfo.getPassword()).equals(user.getPassword())) {
                return Result.of(200, "fail", "密码错误");
            } else {
                String token = JwtHelper.createToken(user.getId(), user.getUsername());
                return Result.of(200, "success", token);
            }
        } else {
            return Result.of(200, "fail", "用户不存在");
        }
    }

    /**
     * 根据token获取用户信息
     */
    @GetMapping("userInfo")
    public Result<UserLoginInfo> userInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        String userName = JwtHelper.getUserName(token);
        UserLoginInfo userLoginInfo = IndexService.getUserInfo(userName);
        return Result.of(200, "success", userLoginInfo);
    }

    @PostMapping("logout")
    public Result<Void> logout() {
        return Result.of(200, "success", null);
    }
}