package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.Pet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 64360
 * @description 针对表【pet(宠物表)】的数据库操作Service
 * @createDate 2026-01-16 10:52:42
 */
public interface PetService extends IService<Pet> {

  /**
   * 分页查询宠物列表
   */
  Page<Pet> getPetPage(String name, Integer type, String breed, Integer status,
      Integer source, Integer gender, Integer page, Integer pageSize);

  /**
   * 更新宠物状态
   */
  boolean updateStatus(Long id, Integer status);

  /**
   * 更新宠物健康状态
   */
  boolean updateHealthStatus(Long id, Integer healthStatus);
}
