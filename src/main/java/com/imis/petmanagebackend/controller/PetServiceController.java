package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.entity.PetService;
import com.imis.petmanagebackend.service.PetServiceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/petService")
@Slf4j
public class PetServiceController {

  @Autowired
  private PetServiceService petServiceService;

  /**
   * 分页查询宠物服务
   */
  @GetMapping("/page")
  public Result<?> getServicePage(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer type,
      @RequestParam(required = false) Integer status,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<PetService> pageInfo = petServiceService.getServicePage(name, type, status, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 获取服务详情
   */
  @GetMapping("/{id}")
  public Result<?> getServiceById(@PathVariable Long id) {
    PetService service = petServiceService.getById(id);
    if (service == null) {
      return Result.fail("服务不存在");
    }
    return Result.success(service);
  }

  /**
   * 新增服务
   */
  @PostMapping
  public Result<?> addService(@RequestBody PetService petService) {
    boolean flag = petServiceService.save(petService);
    return flag ? Result.success("新增成功") : Result.fail("新增失败");
  }

  /**
   * 修改服务
   */
  @PutMapping
  public Result<?> updateService(@RequestBody PetService petService) {
    if (petService.getId() == null) {
      return Result.fail("服务ID不能为空");
    }
    boolean flag = petServiceService.updateById(petService);
    return flag ? Result.success("修改成功") : Result.fail("修改失败");
  }

  /**
   * 删除服务
   */
  @DeleteMapping("/{id}")
  public Result<?> deleteService(@PathVariable Long id) {
    boolean flag = petServiceService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }

  /**
   * 批量删除服务
   */
  @DeleteMapping("/batch")
  public Result<?> batchDeleteService(@RequestBody List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return Result.fail("请选择要删除的服务");
    }
    boolean flag = petServiceService.removeByIds(ids);
    return flag ? Result.success("批量删除成功") : Result.fail("批量删除失败");
  }

  /**
   * 更新服务状态（启用/禁用）
   */
  @PutMapping("/status")
  public Result<?> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    if (status != 0 && status != 1) {
      return Result.fail("状态值无效，只能是0(禁用)或1(启用)");
    }
    boolean flag = petServiceService.updateStatus(id, status);
    return flag ? Result.success("状态更新成功") : Result.fail("状态更新失败");
  }

  /**
   * 获取所有启用的服务（供下拉选择）
   */
  @GetMapping("/list")
  public Result<?> getEnabledServices() {
    List<PetService> list = petServiceService.lambdaQuery()
        .eq(PetService::getStatus, 1)
        .orderByDesc(PetService::getSort)
        .list();
    return Result.success(list);
  }
}
