package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.PetService;
import com.imis.petmanagebackend.service.PetServiceService;
import com.imis.petmanagebackend.mapper.PetServiceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author 64360
 * @description 针对表【pet_service(宠物服务表)】的数据库操作Service实现
 * @createDate 2026-01-21 14:41:23
 */
@Service
@Slf4j
public class PetServiceServiceImpl extends ServiceImpl<PetServiceMapper, PetService>
        implements PetServiceService {

    @Override
    public Page<PetService> getServicePage(String name, Integer type, Integer status, Integer page, Integer pageSize) {
        Page<PetService> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<PetService> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.hasText(name), PetService::getName, name)
                .eq(type != null, PetService::getType, type)
                .eq(status != null, PetService::getStatus, status)
                .orderByDesc(PetService::getSort)
                .orderByDesc(PetService::getCreateTime);

        return this.page(pageInfo, queryWrapper);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<PetService> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(PetService::getId, id)
                .set(PetService::getStatus, status);
        return this.update(updateWrapper);
    }
}
