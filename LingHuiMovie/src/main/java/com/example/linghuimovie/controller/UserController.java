package com.example.linghuimovie.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.linghuimovie.common.AuthAccess;
import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.exception.ServiceException;
import com.example.linghuimovie.service.UserService;
import com.example.linghuimovie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/1/22 8:56
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //新增用户信息
    @PostMapping("/add")
    public Result add(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    //修改用户信息
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    //删除用户信息
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        User currentUser = TokenUtils.getCurrentUser();
        if (id.equals(currentUser.getId())) {
            throw new ServiceException("不能删除当前的用户");
        }
        userService.removeById(id);
        return Result.success();
    }

    //批量删除用户信息
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        User currentUser = TokenUtils.getCurrentUser();
        if (ids.equals(currentUser.getId())) {
            throw new ServiceException("不能删除当前的用户");
        }
        userService.removeBatchByIds(ids);
        return Result.success();
    }
    @AuthAccess
    //查询全部用户信息
    @GetMapping("/selectAll")
        public Result selectAll() {
            List<User> userList = userService.list( new QueryWrapper<>());
            return Result.success(userList);
    }
    //根据id查询用户信息
    @GetMapping("/selectById/{id}")
        public Result selectById(@PathVariable Integer id) {
            User user = userService.getById(id);
            return Result.success(user);
    }

    //多条件分页查询用户信息
    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam String username,
                               @RequestParam String phone) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().orderByDesc("id");
        queryWrapper.like(StrUtil.isNotBlank(username), "username", username);
        queryWrapper.like(StrUtil.isNotBlank(phone), "phone", phone);
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }



}