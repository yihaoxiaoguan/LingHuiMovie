package com.example.linghuimovie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/1/19 18:16
 */

@Data
@TableName("user")
public class User {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;

    @TableField(exist = false)
    private String token;
}
