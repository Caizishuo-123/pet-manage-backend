package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.service.AdoptionApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/adoption")
@Slf4j
public class AdoptionApplyController {

  @Autowired
  private AdoptionApplyService adoptionApplyService;

  /**
   * 分页查询领养申请
   */
  @GetMapping("/page")
  public Result<?> getApplyPage(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Long petId,
      @RequestParam(required = false) Integer status,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Map<String, Object>> pageInfo = adoptionApplyService.getApplyPage(userId, petId, status, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 获取申请详情
   */
  @GetMapping("/{id}")
  public Result<?> getApplyDetail(@PathVariable Long id) {
    Map<String, Object> detail = adoptionApplyService.getApplyDetail(id);
    if (detail == null) {
      return Result.fail("申请记录不存在");
    }
    return Result.success(detail);
  }

  /**
   * 审核领养申请
   * 
   * @param id     申请ID
   * @param status 状态：2-通过 3-拒绝
   */
  @PutMapping("/audit")
  public Result<?> audit(@RequestParam Long id, @RequestParam Integer status) {
    if (status != 2 && status != 3) {
      return Result.fail("状态值无效，只能是2(通过)或3(拒绝)");
    }
    boolean flag = adoptionApplyService.audit(id, status);
    return flag ? Result.success("审核完成") : Result.fail("审核失败");
  }

  /**
   * 删除申请记录
   */
  @DeleteMapping("/{id}")
  public Result<?> deleteApply(@PathVariable Long id) {
    boolean flag = adoptionApplyService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }
}
