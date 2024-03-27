package com.example.linghuimovie.mapper;

import com.example.linghuimovie.entity.Carousel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarouselMapper {

    // 添加轮播图
    @Insert("INSERT INTO carousel (image_url, link_url) VALUES (#{imageUrl}, #{linkUrl})")
    void insertCarousel(Carousel carousel);


    // 查询所有轮播图
    @Select("SELECT * FROM 'carousel'")
    List<Carousel> selectAll();

    // 根据id删除轮播图
    @Delete("DELETE FROM 'carousel' WHERE id = #{id}")
    void deleteById(Integer id);


    // 批量删除轮播图
    @Delete("DELETE FROM 'carousel' WHERE id IN (#{ids})")
    void deleteBatch(List<Integer> ids);


    // 更新轮播图
    @Update("UPDATE 'carousel' SET image_url = #{imageUrl}, link_url = #{linkUrl} WHERE id = #{id}")
    void updateCarousel(Carousel carousel);
}

