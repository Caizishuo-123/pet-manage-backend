package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.Orders;
import com.imis.petmanagebackend.service.OrdersService;

import lombok.extern.slf4j.Slf4j;

import com.imis.petmanagebackend.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 64360
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2026-01-21 14:23:29
*/
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




