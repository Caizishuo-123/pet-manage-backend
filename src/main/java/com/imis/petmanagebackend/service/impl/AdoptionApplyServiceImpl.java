package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.AdoptionApply;
import com.imis.petmanagebackend.entity.Pet;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.AdoptionApplyService;
import com.imis.petmanagebackend.service.PetService;
import com.imis.petmanagebackend.service.UserService;

import lombok.extern.slf4j.Slf4j;

import com.imis.petmanagebackend.mapper.AdoptionApplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 64360
 * @description 针对表【adoption_apply(领养申请表)】的数据库操作Service实现
 * @createDate 2026-01-21 14:23:32
 */
@Service
@Slf4j
public class AdoptionApplyServiceImpl extends ServiceImpl<AdoptionApplyMapper, AdoptionApply>
        implements AdoptionApplyService {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Override
    public Page<Map<String, Object>> getApplyPage(Long userId, Long petId, Integer status, Integer page,
            Integer pageSize) {
        // 1. 分页查询申请记录
        Page<AdoptionApply> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<AdoptionApply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, AdoptionApply::getUserId, userId)
                .eq(petId != null, AdoptionApply::getPetId, petId)
                .eq(status != null, AdoptionApply::getStatus, status)
                .orderByDesc(AdoptionApply::getCreateTime);

        Page<AdoptionApply> applyPage = this.page(pageInfo, queryWrapper);

        // 2. 封装结果（带用户名、宠物名）
        Page<Map<String, Object>> resultPage = new Page<>(page, pageSize);
        resultPage.setTotal(applyPage.getTotal());

        List<Map<String, Object>> records = applyPage.getRecords().stream().map(apply -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", apply.getId());
            map.put("userId", apply.getUserId());
            map.put("petId", apply.getPetId());
            map.put("applyReason", apply.getApplyReason());
            map.put("status", apply.getStatus());
            map.put("createTime", apply.getCreateTime());
            map.put("updateTime", apply.getUpdateTime());

            // 获取用户名
            User user = userService.getById(apply.getUserId());
            map.put("username", user != null ? user.getUsername() : "未知用户");

            // 获取宠物名
            Pet pet = petService.getById(apply.getPetId());
            map.put("petName", pet != null ? pet.getName() : "未知宠物");

            return map;
        }).collect(Collectors.toList());

        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Map<String, Object> getApplyDetail(Long id) {
        AdoptionApply apply = this.getById(id);
        if (apply == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", apply.getId());
        map.put("userId", apply.getUserId());
        map.put("petId", apply.getPetId());
        map.put("applyReason", apply.getApplyReason());
        map.put("status", apply.getStatus());
        map.put("createTime", apply.getCreateTime());
        map.put("updateTime", apply.getUpdateTime());

        // 获取用户信息
        User user = userService.getById(apply.getUserId());
        if (user != null) {
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
        }

        // 获取宠物信息
        Pet pet = petService.getById(apply.getPetId());
        if (pet != null) {
            map.put("petName", pet.getName());
            map.put("petType", pet.getType());
            map.put("petBreed", pet.getBreed());
        }

        return map;
    }

    @Override
    @Transactional
    public boolean audit(Long id, Integer status) {
        // 1. 获取申请记录
        AdoptionApply apply = this.getById(id);
        if (apply == null) {
            return false;
        }

        // 2. 更新申请状态
        LambdaUpdateWrapper<AdoptionApply> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(AdoptionApply::getId, id)
                .set(AdoptionApply::getStatus, status);
        boolean updated = this.update(updateWrapper);

        // 3. 如果审核通过，更新宠物状态和所属者
        if (updated && status == 2) {
            LambdaUpdateWrapper<Pet> petUpdateWrapper = new LambdaUpdateWrapper<>();
            petUpdateWrapper.eq(Pet::getId, apply.getPetId())
                    .set(Pet::getStatus, 2) // 已领养
                    .set(Pet::getOwnerId, apply.getUserId());
            petService.update(petUpdateWrapper);
            log.info("领养申请通过，宠物ID：{}，新主人ID：{}", apply.getPetId(), apply.getUserId());
        }

        return updated;
    }
}
