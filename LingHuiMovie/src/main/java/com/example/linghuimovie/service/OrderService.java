package com.example.linghuimovie.service;

import com.example.linghuimovie.entity.Film;
import com.example.linghuimovie.entity.Order;
import com.example.linghuimovie.entity.User;
import com.example.linghuimovie.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 21:03
 */
@Service
public class OrderService {

    @Resource
    OrderMapper orderMapper;
    // 添加电影
    public void insertOrder(Order order){
        orderMapper.insertOrder(order);
    }

    public Order findByPhone(Integer phone) {
        return orderMapper.findByPhone(phone);
    }

    public List<Order> findAll() {
        return orderMapper.findAll();
    }
}