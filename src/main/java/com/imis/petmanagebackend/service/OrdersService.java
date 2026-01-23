package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.Orders;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 64360
 * @description 针对表【orders(订单表)】的数据库操作Service
 * @createDate 2026-01-21 14:23:29
 */
public interface OrdersService extends IService<Orders> {

  /**
   * 分页查询订单（带关联信息）
   */
  Page<Map<String, Object>> getOrderPage(Long userId, String orderNo, Integer payStatus, Integer page,
      Integer pageSize);

  /**
   * 获取订单详情
   */
  Map<String, Object> getOrderDetail(Long id);

  /**
   * 更新支付状态
   */
  boolean updatePayStatus(Long id, Integer payStatus, Integer payMethod);

  /**
   * 生成订单号
   */
  String generateOrderNo();
}
