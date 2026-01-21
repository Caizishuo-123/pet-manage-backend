package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.Pet;
import com.imis.petmanagebackend.service.PetService;
import com.imis.petmanagebackend.mapper.PetMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 64360
 * @description 针对表【pet(宠物表)】的数据库操作Service实现
 * @createDate 2026-01-16 10:52:42
 */
@Service
@Slf4j
public class PetServiceImpl extends ServiceImpl<PetMapper, Pet>
        implements PetService {

    @Override
    public Page<Pet> getPetPage(String name, Integer type, String breed, Integer status,
            Integer source, Integer gender, Integer page, Integer pageSize) {
        Page<Pet> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Pet> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), Pet::getName, name)
                .eq(type != null, Pet::getType, type)
                .like(StringUtils.hasText(breed), Pet::getBreed, breed)
                .eq(status != null, Pet::getStatus, status)
                .eq(source != null, Pet::getSource, source)
                .eq(gender != null, Pet::getGender, gender)
                .orderByDesc(Pet::getCreateTime);

        return this.page(pageInfo, queryWrapper);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<Pet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Pet::getId, id)
                .set(Pet::getStatus, status);
        return this.update(updateWrapper);
    }

    @Override
    public boolean updateHealthStatus(Long id, Integer healthStatus) {
        LambdaUpdateWrapper<Pet> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Pet::getId, id)
                .set(Pet::getHealthStatus, healthStatus);
        return this.update(updateWrapper);
    }
}
