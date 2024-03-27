package com.example.linghuimovie.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 17:30
 */
@Data
public class Film {
    private Integer movieId;
    private String movieName;
    private String releaseTime;
    private String type;
    private String director;
    private String actors;
    private BigDecimal score;
    private String introduction;
    private BigDecimal boxOffice;
    private Integer duration;
    private String poster;
    private String region;

}