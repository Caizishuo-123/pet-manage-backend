package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.service.ServiceAppointmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/appointment")
@Slf4j
public class ServiceAppointmentController {

  @Autowired
  private ServiceAppointmentService serviceAppointmentService;

  /**
   * 分页查询预约
   */
  @GetMapping("/page")
  public Result<?> getAppointmentPage(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Long petId,
      @RequestParam(required = false) Long serviceId,
      @RequestParam(required = false) Integer status,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Map<String, Object>> pageInfo = serviceAppointmentService.getAppointmentPage(userId, petId, serviceId, status,
        page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 获取预约详情
   */
  @GetMapping("/{id}")
  public Result<?> getAppointmentDetail(@PathVariable Long id) {
    Map<String, Object> detail = serviceAppointmentService.getAppointmentDetail(id);
    if (detail == null) {
      return Result.fail("预约记录不存在");
    }
    return Result.success(detail);
  }

  /**
   * 更新预约状态
   * 
   * @param id     预约ID
   * @param status 状态：1-已预约 2-已完成 3-已取消
   */
  @PutMapping("/status")
  public Result<?> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    if (status < 1 || status > 3) {
      return Result.fail("状态值无效，只能是1(已预约)、2(已完成)或3(已取消)");
    }
    boolean flag = serviceAppointmentService.updateStatus(id, status);
    return flag ? Result.success("状态更新成功") : Result.fail("状态更新失败");
  }

  /**
   * 删除预约记录
   */
  @DeleteMapping("/delete/{id}")
  public Result<?> deleteAppointment(@PathVariable Long id) {
    boolean flag = serviceAppointmentService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }
}
