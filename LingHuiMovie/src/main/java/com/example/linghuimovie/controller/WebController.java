package com.example.linghuimovie.controller;

import cn.hutool.core.util.StrUtil;
import com.example.linghuimovie.common.AuthAccess;
import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/1/16 14:36
 */
@RestController
public class  WebController {
    @Resource
    UserService userService;

    @AuthAccess
    @GetMapping("/")
    public Result hello(){
        return Result.success("success");
    }
    @PostMapping("/login")
    public Result login(@RequestBody User user){
      if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
              return Result.error("用户名或密码不能为空");
      }
    user = userService.login(user);
    return  Result.success(user);
    }
    @AuthAccess
    @PostMapping("/register")
    public Result register(@RequestBody User user){
        if (StrUtil.isBlank(user.getUsername()) || StrUtil.isBlank(user.getPassword())){
            return Result.error("用户名或密码不能为空");
        }
        if (user.getUsername().length() != 11 || user.getPassword().length()>16){
            return Result.error("数据输入不合法");
        }
        user = userService.register(user);
        return  Result.success(user);
    }
}
