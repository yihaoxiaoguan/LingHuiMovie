package com.example.linghuimovie.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/13 21:05
 */
@Data
public class Showtime {
    private Integer showtimeId;
    private Integer hallId;
    private Integer movieId;
    private String date;
    private String startTime;
    private String endTime;
    private BigDecimal price;
    @TableField(exist = false)
    private String hallName; // 新增字段
    @TableField(exist = false)
    private String screenType; // 新增字段
}