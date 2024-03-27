package com.example.linghuimovie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linghuimovie.entity.Showtime;
import com.example.linghuimovie.mapper.ShowtimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/13 21:01
 */
@Service
public class ShowtimeService {

    @Autowired
    private ShowtimeMapper showtimeMapper;

    public List<Showtime> findAll() {
        return showtimeMapper.selectAll();
    }

    public List<Showtime> getShowtimeByCinemaAndMovie(Integer cinemaId, Integer movieId) {
        return showtimeMapper.getShowtimeByCinemaAndMovie(cinemaId, movieId);
    }
}
