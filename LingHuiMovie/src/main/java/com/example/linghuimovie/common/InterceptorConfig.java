package com.example.linghuimovie.common;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/2/27 12:49
 * 说明：自定义配置拦截器
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())  //配置JWT拦截器规则
                .addPathPatterns("/**") //** 拦截所有的请求路径
                .excludePathPatterns("/login");//排除登录路径
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

}