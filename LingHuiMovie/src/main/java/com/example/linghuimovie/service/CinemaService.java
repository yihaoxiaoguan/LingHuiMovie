package com.example.linghuimovie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linghuimovie.entity.Cinema;
import com.example.linghuimovie.mapper.CinemaMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/13 13:42
 */
@Service
public class CinemaService extends ServiceImpl<CinemaMapper, Cinema> {
    @Resource
    CinemaMapper cinemaMapper;
    public List<Cinema> findAll() {
        return cinemaMapper.selectAll();
    }

    public List<Cinema> findByAreaAndBrand(String area, String brand) {
        QueryWrapper<Cinema> wrapper = new QueryWrapper<>();
        if (!area.equals("全部")) {
            wrapper.in("area", area);
        }
        if (!brand.equals("全部")) {
            wrapper.in("brand", brand);
        }
        return cinemaMapper.selectList(wrapper);
    }
    public Cinema findById(Integer id) {
        return cinemaMapper.findById(id);
    }
}