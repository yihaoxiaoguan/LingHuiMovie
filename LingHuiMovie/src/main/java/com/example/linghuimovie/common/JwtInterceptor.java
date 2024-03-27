package com.example.linghuimovie.common;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/2/26 20:01
 * 说明：自定义拦截规则
 */

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.exception.ServiceException;
import com.example.linghuimovie.mapper.UserMapper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("token");   // 从前端的请求头header中传过来的参数token
        if (StrUtil.isBlank(token)) {
            token = request.getParameter("token");  // url中传过来的参数token    格式：？token=xxxxxx
        }
        // 如果不是映射到方法直接通过
        if (handler instanceof HandlerMethod) {
            AuthAccess annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthAccess.class);
            if (annotation != null) {
                return true;
            }
        }
        // 执行认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException("401", "请登录"); //抛出异常
        }
        // 获取 token 中的 user id
        String userId;   //  使用String的原因是因为token是一个字符串，不是int类型
        try {
            userId = JWT.decode(token).getAudience().get(0); //JWT.decode(token) 解析token，获取token中的audience,并拿到第一个数据
        } catch (JWTDecodeException j) {
            throw new ServiceException("401", "请登录");
        }
        // 根据token中的userid查询数据库
        User user = userMapper.selectById(Integer.valueOf(userId));
        if (user == null) {
            throw new ServiceException("401", "请登录");
        }
        //  通过用户密码加密之后生成一个验证器   加签验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token); // 验证token
        } catch (JWTVerificationException e) {
            throw new ServiceException("401", "请登录");
        }
        return true;
    }
}