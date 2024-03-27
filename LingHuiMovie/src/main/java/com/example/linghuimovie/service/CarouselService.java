package com.example.linghuimovie.service;

import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Carousel;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.mapper.CarouselMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 23:46
 */
@Service
public class CarouselService {

    @Autowired
    CarouselMapper carouselMapper;

    //新增轮播图
    public void insertCarousel(Carousel carousel){
        carouselMapper.insertCarousel(carousel);
    }
    //查询所有轮播图
    public List<Carousel> selectAll() {
        return carouselMapper.selectAll();
    }

    //删除轮播图
    public void deleteById(Integer id) {
        carouselMapper.deleteById(id);
    }

    //批量删除轮播图
    public void deleteBatch(List<Integer> ids) {
        carouselMapper.deleteBatch(ids);
    }

    public void updateCarousel(Carousel carousel) {
        carouselMapper.updateCarousel(carousel);
    }
    //修改轮播图
}