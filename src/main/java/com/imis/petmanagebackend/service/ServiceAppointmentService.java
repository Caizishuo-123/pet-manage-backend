package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.ServiceAppointment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 64360
 * @description 针对表【service_appointment(服务预约表)】的数据库操作Service
 * @createDate 2026-01-21 14:23:17
 */
public interface ServiceAppointmentService extends IService<ServiceAppointment> {

  /**
   * 分页查询预约（带用户名、宠物名、服务名）
   */
  Page<Map<String, Object>> getAppointmentPage(Long userId, Long petId, Long serviceId, Integer status, Integer page,
      Integer pageSize);

  /**
   * 获取预约详情（带关联信息）
   */
  Map<String, Object> getAppointmentDetail(Long id);

  /**
   * 更新预约状态
   */
  boolean updateStatus(Long id, Integer status);
}
