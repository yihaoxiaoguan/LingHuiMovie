package com.example.linghuimovie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.linghuimovie.entity.Cinema;
import com.example.linghuimovie.entity.Film;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CinemaMapper extends BaseMapper<Cinema> {
    @Select("select * from `cinema`")
    List<Cinema> selectAll();

    @Select("select * from `cinema` where cinema_id=#{cinemaId}")
    Cinema findById(Integer id);
}
