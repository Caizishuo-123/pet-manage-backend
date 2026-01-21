package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.Pet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 64360
* @description 针对表【pet(宠物表)】的数据库操作Mapper
* @createDate 2026-01-16 10:52:42
* @Entity com.imis.petmanagebackend.entity.Pet
*/
@Repository
@Mapper
public interface PetMapper extends BaseMapper<Pet> {

}




