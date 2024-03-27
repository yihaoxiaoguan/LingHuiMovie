package com.example.linghuimovie.controller;

import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Showtime;
import com.example.linghuimovie.service.ShowtimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/13 20:56
 */
@RestController

@RequestMapping("/showtime")
public class ShowtimeController {

    @Autowired
    ShowtimeService showtimeService;

    @GetMapping("/all")
    public Result findAll() {
        List<Showtime> showtimeList = showtimeService.findAll();
        return Result.success(showtimeList);
    }

    @GetMapping("/selectByCAM")
    public List<Showtime> getShowtimeByCinemaAndMovie(@RequestParam("cinemaId") Integer cinemaId, @RequestParam("movieId") Integer movieId) {
        return showtimeService.getShowtimeByCinemaAndMovie(cinemaId, movieId);
    }
}