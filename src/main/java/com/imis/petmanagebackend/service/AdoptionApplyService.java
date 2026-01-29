package com.imis.petmanagebackend.service;

import com.imis.petmanagebackend.entity.AdoptionApply;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 64360
 * @description 针对表【adoption_apply(领养申请表)】的数据库操作Service
 * @createDate 2026-01-21 14:23:32
 */
public interface AdoptionApplyService extends IService<AdoptionApply> {

  /**
   * 分页查询领养申请（带用户名、宠物名）
   */
  Page<Map<String, Object>> getApplyPage(Long userId, Long petId, Integer status, Integer page, Integer pageSize);

  /**
   * 获取申请详情（带用户名、宠物名）
   */
  Map<String, Object> getApplyDetail(Long id);

  /**
   * 审核领养申请
   * 
   * @param id     申请ID
   * @param status 状态：2-通过 3-拒绝 4-已完成
   * @return 是否成功
   */
  boolean audit(Long id, Integer status);
}
