package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.entity.Pet;
import com.imis.petmanagebackend.entity.ServiceAppointment;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.PetServiceService;
import com.imis.petmanagebackend.service.ServiceAppointmentService;
import com.imis.petmanagebackend.service.UserService;

import lombok.extern.slf4j.Slf4j;

import com.imis.petmanagebackend.mapper.ServiceAppointmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 64360
 * @description 针对表【service_appointment(服务预约表)】的数据库操作Service实现
 * @createDate 2026-01-21 14:23:17
 */
@Service
@Slf4j
public class ServiceAppointmentServiceImpl extends ServiceImpl<ServiceAppointmentMapper, ServiceAppointment>
        implements ServiceAppointmentService {

    @Autowired
    private UserService userService;

    @Autowired
    private com.imis.petmanagebackend.service.PetService petService;

    @Autowired
    private PetServiceService petServiceService;

    @Override
    public Page<Map<String, Object>> getAppointmentPage(Long userId, Long petId, Long serviceId, Integer status,
            Integer page, Integer pageSize) {
        Page<ServiceAppointment> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<ServiceAppointment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(userId != null, ServiceAppointment::getUserId, userId)
                .eq(petId != null, ServiceAppointment::getPetId, petId)
                .eq(serviceId != null, ServiceAppointment::getServiceId, serviceId)
                .eq(status != null, ServiceAppointment::getStatus, status)
                .orderByDesc(ServiceAppointment::getCreateTime);

        Page<ServiceAppointment> appointmentPage = this.page(pageInfo, queryWrapper);

        Page<Map<String, Object>> resultPage = new Page<>(page, pageSize);
        resultPage.setTotal(appointmentPage.getTotal());

        List<Map<String, Object>> records = appointmentPage.getRecords().stream().map(appointment -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", appointment.getId());
            map.put("userId", appointment.getUserId());
            map.put("petId", appointment.getPetId());
            map.put("serviceId", appointment.getServiceId());
            map.put("appointmentTime", appointment.getAppointmentTime());
            map.put("status", appointment.getStatus());
            map.put("remark", appointment.getRemark());
            map.put("createTime", appointment.getCreateTime());

            // 获取用户名
            User user = userService.getById(appointment.getUserId());
            map.put("username", user != null ? user.getUsername() : "未知用户");

            // 获取宠物名
            Pet pet = petService.getById(appointment.getPetId());
            map.put("petName", pet != null ? pet.getName() : "未知宠物");

            // 获取服务名
            com.imis.petmanagebackend.entity.PetService service = petServiceService.getById(appointment.getServiceId());
            map.put("serviceName", service != null ? service.getName() : "未知服务");
            map.put("servicePrice", service != null ? service.getPrice() : null);

            return map;
        }).collect(Collectors.toList());

        resultPage.setRecords(records);
        return resultPage;
    }

    @Override
    public Map<String, Object> getAppointmentDetail(Long id) {
        ServiceAppointment appointment = this.getById(id);
        if (appointment == null) {
            return null;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("id", appointment.getId());
        map.put("userId", appointment.getUserId());
        map.put("petId", appointment.getPetId());
        map.put("serviceId", appointment.getServiceId());
        map.put("appointmentTime", appointment.getAppointmentTime());
        map.put("status", appointment.getStatus());
        map.put("remark", appointment.getRemark());
        map.put("createTime", appointment.getCreateTime());

        // 获取用户信息
        User user = userService.getById(appointment.getUserId());
        if (user != null) {
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
        }

        // 获取宠物信息
        Pet pet = petService.getById(appointment.getPetId());
        if (pet != null) {
            map.put("petName", pet.getName());
            map.put("petType", pet.getType());
            map.put("petBreed", pet.getBreed());
        }

        // 获取服务信息
        com.imis.petmanagebackend.entity.PetService service = petServiceService.getById(appointment.getServiceId());
        if (service != null) {
            map.put("serviceName", service.getName());
            map.put("serviceType", service.getType());
            map.put("servicePrice", service.getPrice());
        }

        return map;
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        LambdaUpdateWrapper<ServiceAppointment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ServiceAppointment::getId, id)
                .set(ServiceAppointment::getStatus, status);
        return this.update(updateWrapper);
    }
}
