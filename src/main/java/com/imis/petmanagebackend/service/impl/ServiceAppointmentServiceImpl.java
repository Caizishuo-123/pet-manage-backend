package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.ServiceAppointment;
import com.imis.petmanagebackend.service.ServiceAppointmentService;

import lombok.extern.slf4j.Slf4j;

import com.imis.petmanagebackend.mapper.ServiceAppointmentMapper;
import org.springframework.stereotype.Service;

/**
* @author 64360
* @description 针对表【service_appointment(服务预约表)】的数据库操作Service实现
* @createDate 2026-01-21 14:23:17
*/
@Service
@Slf4j
public class ServiceAppointmentServiceImpl extends ServiceImpl<ServiceAppointmentMapper, ServiceAppointment>
    implements ServiceAppointmentService{

}




