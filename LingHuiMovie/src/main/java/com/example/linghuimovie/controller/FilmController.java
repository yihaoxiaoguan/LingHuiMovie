package com.example.linghuimovie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.linghuimovie.common.AuthAccess;
import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Film;
import com.example.linghuimovie.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.linghuimovie.exception.ServiceException;

import java.util.List;

/**
 * 功能：电影控制器
 * 作者：壹号小馆
 * 日期：2024/3/4 15:33
 */
@RestController

@RequestMapping("/film")
public class FilmController {

    @Autowired
    FilmService filmService;

    //添加影片
    @PostMapping("/add")
    public Result addFilm(@RequestBody Film film) {
        filmService.insertFilm(film);
        return Result.success();
    }

    @PutMapping("/update")
    public Result updateFilm(@RequestBody Film film) {
        filmService.updatefilm(film);
        return Result.success();
    }


    //根据id查询影片详情
    @AuthAccess
    @GetMapping("/{id}")
    public Film findById(@PathVariable Integer id) {
        return filmService.findById(id);
    }

    @AuthAccess
    //查询所有影片
    @GetMapping("/all")
    public Result findAll() {
        List<Film> filmList = filmService.findAll();
        return Result.success(filmList);
    }


    //根据电影名称查询
    @GetMapping("/findByName/{name}")
    public List<Film> search(@PathVariable String name) {
        return filmService.findLikeName(name);
    }

    //删除影片
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable Integer id) {
        filmService.removeById(id);
        return Result.success();
    }

    @GetMapping("/selectByPage")
    public Result selectByPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize) {
        QueryWrapper<Film> queryWrapper = new QueryWrapper<Film>().orderByDesc("movie_id");
        Page<Film> page = filmService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }
    @GetMapping("/selectByRAT")
    public List<Film> list(String region, String type) {
        if (region != null && type != null) {
            return filmService.findByRegionAndType(region, type);
        }
        return filmService.findAll();
    }
}