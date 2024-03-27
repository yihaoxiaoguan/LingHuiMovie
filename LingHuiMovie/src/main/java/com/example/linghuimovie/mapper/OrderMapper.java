package com.example.linghuimovie.mapper;

import com.example.linghuimovie.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 21:06
 */
@Mapper
public interface OrderMapper {

    //插入订单
    @Insert("insert into `order`(user_id, movie_name, seats, price, create_time) values(#{userId}, #{movieName}, #{seats}, #{price}, #{createTime})")
    void insertOrder(Order order);

    //根据手机号查询订单
    //注意：这里的#{phone}对应的是前端传递过来的参数，也就是手机号
    @Select("select * from `order` where phone = #{phone}")
    Order findByPhone(Integer phone);

    //查询所有订单
    @Select("select * from `order`")
    //注意：这里返回的是一个集合，因为可能存在多个订单
    List<Order> findAll();
}