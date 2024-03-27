package com.example.linghuimovie.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.linghuimovie.entity.Film;
import com.example.linghuimovie.mapper.FilmMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 16:09
 */
@Service
public class FilmService extends ServiceImpl<FilmMapper, Film> {

    @Resource
    FilmMapper filmMapper;

    // 添加电影
    public void insertFilm(Film film) {
        filmMapper.insert(film);
    }

    // 根据地区和类型查询电影

    // 查询所有电影
    public List<Film> findAll() {
        return filmMapper.selectAll();
    }

    // 根据id查询电影
    public Film findById(Integer id) {
        return filmMapper.findById(id);
    }

    // 更新电影
    public Film update(Film film) {
        filmMapper.updateById(film);
        return film;
    }

    // 根据id删除电影
    public void deleteById(Integer movieId) {
        filmMapper.deleteById(movieId);
    }

    // 根据名称查询电影
    public List<Film> findByName(String name) {
        return filmMapper.findByName(name);
    }


    public void updatefilm(Film film) {
        filmMapper.updatefilm(film);
    }

    public List<Film> findLikeName(String name) {
        QueryWrapper<Film> wrapper = new QueryWrapper<>();
        wrapper.like("movie_name", name);
        return filmMapper.selectList(wrapper);
    }

    public List<Film> findByRegionAndType(String region, String type) {
        QueryWrapper<Film> wrapper = new QueryWrapper<>();
        if (!region.equals("全部")) {
            wrapper.in("region", region);
        }
        if (!type.equals("全部")) {
            wrapper.in("type", type);
        }
        return filmMapper.selectList(wrapper);
    }
}