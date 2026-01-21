package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.AdoptionApply;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 64360
* @description 针对表【adoption_apply(领养申请表)】的数据库操作Mapper
* @createDate 2026-01-21 14:23:32
* @Entity com.imis.petmanagebackend.entity.AdoptionApply
*/
@Repository
@Mapper
public interface AdoptionApplyMapper extends BaseMapper<AdoptionApply> {

}




