package com.example.linghuimovie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Film;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FilmMapper extends BaseMapper<Film> {
   //添加电影

    //根据id查询电影
    @Select("select * from `film` where movie_id=#{movieId}")
    Film findById(Integer id);

    //更新电影
   //删除电影
    @Delete("delete from `film` where movie_id=#{movieId}")
    void deleteById(Integer movieId);

  //根据名称查询电影
    @Select("select * from `film` where movie_name=#{movieName} ")
     List<Film> findByName(String name);


    //查询所有电影
    @Select("select * from `film`")
    List<Film> selectAll();



    @Update("update `film` set movie_name=#{movieName},release_time=#{releaseTime},type=#{type},director=#{director},actors=#{actors},score=#{score},introduction=#{introduction},box_office=#{boxOffice},duration=#{duration},poster=#{poster},region=#{region} where movie_id=#{movieId} ")
    void updatefilm(Film film);

}
