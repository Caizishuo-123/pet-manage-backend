package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/orders")
@Slf4j
public class OrdersController {

  @Autowired
  private OrdersService ordersService;

  /**
   * 分页查询订单
   */
  @GetMapping("/page")
  public Result<?> getOrderPage(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) String orderNo,
      @RequestParam(required = false) Integer payStatus,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Map<String, Object>> pageInfo = ordersService.getOrderPage(userId, orderNo, payStatus, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 获取订单详情
   */
  @GetMapping("/{id}")
  public Result<?> getOrderDetail(@PathVariable Long id) {
    Map<String, Object> detail = ordersService.getOrderDetail(id);
    if (detail == null) {
      return Result.fail("订单不存在");
    }
    return Result.success(detail);
  }

  /**
   * 更新支付状态
   * 
   * @param id        订单ID
   * @param payStatus 支付状态：0-未支付 1-已支付
   * @param payMethod 支付方式：1-微信 2-支付宝（可选）
   */
  @PutMapping("/payStatus")
  public Result<?> updatePayStatus(
      @RequestParam Long id,
      @RequestParam Integer payStatus,
      @RequestParam(required = false) Integer payMethod) {
    if (payStatus != 0 && payStatus != 1) {
      return Result.fail("支付状态值无效，只能是0(未支付)或1(已支付)");
    }
    boolean flag = ordersService.updatePayStatus(id, payStatus, payMethod);
    return flag ? Result.success("支付状态更新成功") : Result.fail("支付状态更新失败");
  }

  /**
   * 删除订单
   */
  @DeleteMapping("/{id}")
  public Result<?> deleteOrder(@PathVariable Long id) {
    boolean flag = ordersService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }
}
