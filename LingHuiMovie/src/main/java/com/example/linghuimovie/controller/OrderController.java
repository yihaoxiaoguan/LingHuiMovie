package com.example.linghuimovie.controller;

import com.example.linghuimovie.common.AuthAccess;
import com.example.linghuimovie.common.Result;
import com.example.linghuimovie.entity.Film;
import com.example.linghuimovie.entity.Order;
import com.example.linghuimovie.service.FilmService;
import com.example.linghuimovie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 功能：
 * 作者：壹号小馆
 * 日期：2024/3/4 21:00
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    //新增订单
    @PostMapping("/add")
    public Result addFilm(@RequestBody Order order) {
        orderService.insertOrder(order);
        return Result.success();
    }

    //根据手机号查询订单
    @AuthAccess
    @GetMapping("/{phone}")
    public Result findByPhone(@PathVariable Integer phone) {
        Order order = orderService.findByPhone(phone);
        return Result.success(order);
    }
    //查询所有订单
    @GetMapping("/all")
    public Result findAll() {
        List<Order> OrderList = orderService.findAll();
        return Result.success(OrderList);
    }

}
