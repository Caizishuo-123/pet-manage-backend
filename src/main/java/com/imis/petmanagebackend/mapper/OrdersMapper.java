package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.Orders;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 64360
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2026-01-21 14:23:29
* @Entity com.imis.petmanagebackend.entity.Orders
*/
@Repository
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




