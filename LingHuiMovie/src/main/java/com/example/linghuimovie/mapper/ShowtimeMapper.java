package com.example.linghuimovie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.linghuimovie.entity.Cinema;
import com.example.linghuimovie.entity.Showtime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShowtimeMapper extends BaseMapper<Showtime> {
    @Select("select * from `showtime`")
    List<Showtime> selectAll();

    @Select({
            "<script>",
            "SELECT *, hall.name AS hallName, hall.screen_type AS screenType",
            "FROM showtime",
            "JOIN hall ON showtime.hall_id = hall.hall_id",
            "WHERE hall.cinema_id = #{cinemaId} AND showtime.movie_id = #{movieId}",
            "</script>"
    })
    List<Showtime> getShowtimeByCinemaAndMovie(@Param("cinemaId") Integer cinemaId, @Param("movieId") Integer movieId);
}
