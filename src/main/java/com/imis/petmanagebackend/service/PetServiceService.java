package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.PetService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 64360
 * @description 针对表【pet_service(宠物服务表)】的数据库操作Service
 * @createDate 2026-01-21 14:41:23
 */
public interface PetServiceService extends IService<PetService> {

  /**
   * 分页查询宠物服务
   */
  Page<PetService> getServicePage(String name, Integer type, Integer status, Integer page, Integer pageSize);

  /**
   * 更新服务状态
   */
  boolean updateStatus(Long id, Integer status);
}
