package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.entity.Pet;
import com.imis.petmanagebackend.service.PetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/pet")
@Slf4j
public class PetController {

  @Autowired
  private PetService petService;

  /**
   * 分页查询宠物列表
   */
  @GetMapping("/page")
  public Result<?> getPetPage(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) Integer type,
      @RequestParam(required = false) String breed,
      @RequestParam(required = false) Integer status,
      @RequestParam(required = false) Integer source,
      @RequestParam(required = false) Integer gender,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    Page<Pet> pageInfo = petService.getPetPage(name, type, breed, status, source, gender, page, pageSize);
    return Result.success(pageInfo);
  }

  /**
   * 根据ID获取宠物详情
   */
  @GetMapping("/{id}")
  public Result<?> getPetById(@PathVariable Long id) {
    Pet pet = petService.getById(id);
    if (pet == null) {
      return Result.fail("宠物不存在");
    }
    return Result.success(pet);
  }

  /**
   * 新增宠物
   */
  @PostMapping("/add")
  public Result<?> addPet(@RequestBody Pet pet) {
    boolean flag = petService.save(pet);
    return flag ? Result.success("新增成功") : Result.fail("新增失败");
  }

  /**
   * 修改宠物信息
   */
  @PutMapping("/update")
  public Result<?> updatePet(@RequestBody Pet pet) {
    if (pet.getId() == null) {
      return Result.fail("宠物ID不能为空");
    }
    boolean flag = petService.updateById(pet);
    return flag ? Result.success("修改成功") : Result.fail("修改失败");
  }

  /**
   * 删除宠物
   */
  @DeleteMapping("/delete/{id}")
  public Result<?> deletePet(@PathVariable Long id) {
    boolean flag = petService.removeById(id);
    return flag ? Result.success("删除成功") : Result.fail("删除失败");
  }

  /**
   * 批量删除宠物
   */
  @DeleteMapping("/delete/batch")
  public Result<?> batchDeletePet(@RequestBody List<Long> ids) {
    if (ids == null || ids.isEmpty()) {
      return Result.fail("请选择要删除的宠物");
    }
    boolean flag = petService.removeByIds(ids);
    return flag ? Result.success("批量删除成功") : Result.fail("批量删除失败");
  }

  /**
   * 修改宠物状态
   */
  @PutMapping("/status")
  public Result<?> updateStatus(@RequestParam Long id, @RequestParam Integer status) {
    boolean flag = petService.updateStatus(id, status);
    return flag ? Result.success("状态更新成功") : Result.fail("状态更新失败");
  }

  /**
   * 更新宠物健康状态
   */
  @PutMapping("/healthStatus")
  public Result<?> updateHealthStatus(@RequestParam Long id, @RequestParam Integer healthStatus) {
    boolean flag = petService.updateHealthStatus(id, healthStatus);
    return flag ? Result.success("健康状态更新成功") : Result.fail("健康状态更新失败");
  }
}
