package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.PetService;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 64360
* @description 针对表【pet_service(宠物服务表)】的数据库操作Mapper
* @createDate 2026-01-21 14:41:23
* @Entity com.imis.petmanagebackend.entity.PetService
*/
@Repository
@Mapper
public interface PetServiceMapper extends BaseMapper<PetService> {

}




