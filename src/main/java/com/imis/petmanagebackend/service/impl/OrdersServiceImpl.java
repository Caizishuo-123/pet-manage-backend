package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.Orders;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.OrdersService;
import com.imis.petmanagebackend.service.ServiceAppointmentService;
import com.imis.petmanagebackend.service.UserService;

import lombok.extern.slf4j.Slf4j;

import com.imis.petmanagebackend.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 64360
 * @description 针对表【orders(订单表)】的数据库操作Service实现
 * @createDate 2026-01-21 14:23:29
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
        implements OrdersService {

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceAppointmentService serviceAppointmentService;

    @Override
    public Page<Map<String, Object>> getOrderPage(Long userId, String orderNo, Integer payStatus, Integer page,
            Integer pageSize) {
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, Orders::getUserId, userId)
                .like(StringUtils.hasText(orderNo), Orders::getOrderNo, orderNo)
                .eq(payStatus != null, Orders::getPayStatus, payStatus)
                .orderByDesc(Orders::getCreateTime);

        Page<Orders> ordersPage = this.page(pageInfo, queryWrapper);

        Page<Map<String, Object>> resultPage = new Page<>(page, pageSize);
        resultPage.setTotal(ordersPage.getTotal());

        List<Map<String, Object>> records = ordersPage.getRecords().stream().map(order -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", order.getId());
            map.put("orderNo", order.getOrderNo());
            map.put("userId", order.getUserId());
            map.put("appointmentId", order.getAppointmentId());
            map.put("totalPrice", order.getTotalPrice());
            map.put("payStatus", order.getPayStatus());
            map.put("payTime", order.getPayTime());
            map.put("payMethod", order.getPayMethod());
            map.put("remark", order.getRemark());
            map.put("createTime", order.getCreateTime());

            // 获取用户名
            User user = userService.getById(order.getUserId());
            map.put("username", user != null ? user.getUsername() : "未知用户");

            return map;
        }).collect(Collectors.toList());

        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Map<String, Object> getOrderDetail(Long id) {
        Orders order = this.getById(id);
        if (order == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", order.getId());
        map.put("orderNo", order.getOrderNo());
        map.put("userId", order.getUserId());
        map.put("appointmentId", order.getAppointmentId());
        map.put("totalPrice", order.getTotalPrice());
        map.put("payStatus", order.getPayStatus());
        map.put("payTime", order.getPayTime());
        map.put("payMethod", order.getPayMethod());
        map.put("remark", order.getRemark());
        map.put("createTime", order.getCreateTime());

        // 获取用户信息
        User user = userService.getById(order.getUserId());
        if (user != null) {
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
        }

        // 获取预约信息
        Map<String, Object> appointmentDetail = serviceAppointmentService
                .getAppointmentDetail(order.getAppointmentId());
        if (appointmentDetail != null) {
            map.put("appointmentTime", appointmentDetail.get("appointmentTime"));
            map.put("petName", appointmentDetail.get("petName"));
            map.put("serviceName", appointmentDetail.get("serviceName"));
        }

        return map;
    }

    @Override
    public boolean updatePayStatus(Long id, Integer payStatus, Integer payMethod) {
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Orders::getId, id)
                .set(Orders::getPayStatus, payStatus)
                .set(payMethod != null, Orders::getPayMethod, payMethod)
                .set(payStatus == 1, Orders::getPayTime, new Date());
        return this.update(updateWrapper);
    }

    @Override
    public String generateOrderNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateStr = sdf.format(new Date());
        String random = String.format("%04d", new Random().nextInt(10000));
        return "PO" + dateStr + random;
    }
}
