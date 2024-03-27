package com.example.linghuimovie.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.exception.ServiceException;
import com.example.linghuimovie.mapper.UserMapper;
import com.example.linghuimovie.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

    @Resource
    UserMapper userMapper;

    @Override
    public boolean save(User entity) {
        if (StrUtil.isBlank(entity.getRole())){
            entity.setRole("用户");
        }
        if (StrUtil.isBlank(entity.getAvatar())){
            entity.setAvatar("http://localhost:8081/file/download/03b0d39583f48206768a7534e55.png");
        }
        return super.save(entity);
    }

    public User selectByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);  //  eq => ==   where username = #{username}
        // 根据用户名查询数据库的用户信息
        return getOne(queryWrapper); //  select * from user where username = #{username}
    }
    public User selectByName(String name) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name);  //  eq => ==   where username = #{username}
        // 根据用户名查询数据库的用户信息
        return getOne(queryWrapper); //  select * from user where username = #{username}
    }

    // 验证用户账户是否合法
    public User login(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名或密码错误");
        }
        if (!user.getPassword().equals(dbUser.getPassword())) {
            throw new ServiceException("用户名或密码错误");
        }
        // 生成token
        String token = TokenUtils.createToken(dbUser.getId().toString(), dbUser.getPassword());
        dbUser.setToken(token);
        return dbUser;
    }

    public User register(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser != null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户名已存在");
        }
        user.setName(user.getUsername());
        userMapper.insert(user);
        return user;
    }

    public void resetPassword(User user) {
        User dbUser = selectByUsername(user.getUsername());
        if (dbUser == null) {
            // 抛出一个自定义的异常
            throw new ServiceException("用户不存在");
        }
        if (!user.getPhone().equals(dbUser.getPhone())) {
            throw new ServiceException("验证错误");
        }
        dbUser.setPassword("123");   // 重置密码
        updateById(dbUser);
    }
}