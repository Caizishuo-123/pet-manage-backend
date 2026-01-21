package com.imis.petmanagebackend.mapper;

import com.imis.petmanagebackend.entity.ServiceAppointment;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 64360
* @description 针对表【service_appointment(服务预约表)】的数据库操作Mapper
* @createDate 2026-01-21 14:23:17
* @Entity com.imis.petmanagebackend.entity.ServiceAppointment
*/
@Repository
@Mapper
public interface ServiceAppointmentMapper extends BaseMapper<ServiceAppointment> {

}




