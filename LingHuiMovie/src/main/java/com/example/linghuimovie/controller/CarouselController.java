package com.example.linghuimovie.controller;

import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Carousel;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 23:22
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    CarouselService carouselService;

    //新增轮播图
    @PostMapping("/add")
    public Result add(@RequestBody Carousel carousel) {
        carouselService.insertCarousel(carousel);
        return Result.success();
    }
    //查询所有轮播图
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Carousel> carouselList = carouselService.selectAll();
        return Result.success(carouselList);
    }
    //删除轮播图
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        carouselService.deleteById(id);
        return Result.success();
    }

    //批量删除轮播图
    @DeleteMapping("/delete/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids){
        carouselService.deleteBatch(ids);
        return Result.success();
    }
    //修改轮播图
    @PutMapping("/update")
    public Result update(@RequestBody Carousel carousel) {
        carouselService.updateCarousel(carousel);
        return Result.success();
    }


}