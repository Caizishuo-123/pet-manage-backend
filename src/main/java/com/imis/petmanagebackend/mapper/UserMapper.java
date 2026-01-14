package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author 64360
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2026-01-11 21:07:15
* @Entity com.imis.petmanagebackend.entity.User
*/
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




