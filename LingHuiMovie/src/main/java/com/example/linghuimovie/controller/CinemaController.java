package com.example.linghuimovie.controller;

import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Cinema;
import com.example.linghuimovie.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/13 13:39
 */

@RestController

@RequestMapping("/cinema")
public class CinemaController {

    @Autowired
    CinemaService cinemaService;

    @GetMapping("/selectById/{id}")
    public Cinema findById(@PathVariable Integer id) {
        return cinemaService.findById(id);
    }
    @GetMapping("/all")
    public Result findAll() {
        List<Cinema> cinemaList = cinemaService.findAll();
        return Result.success(cinemaList);
    }
    @GetMapping("/selectByAAB")
    public List<Cinema> list(String area, String brand) {
        if (area != null && brand != null) {
            return cinemaService.findByAreaAndBrand(area, brand);
        }
        return cinemaService.findAll();
    }

}