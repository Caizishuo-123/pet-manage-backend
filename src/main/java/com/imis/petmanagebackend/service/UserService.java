package com.imis.petmanagebackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.entity.LoginVO;
import com.imis.petmanagebackend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 64360
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2026-01-11 21:07:15
*/
public interface UserService extends IService<User> {

    LoginVO login(String account, String password);

    boolean register(User user);

    Page<User> getUserPage(Long id, String username, String phone, Integer role, Integer status, Integer page, Integer pageSize);

    boolean toggleUserStatus(Long id);

    boolean toggleUserRole(Long id, Integer role);
}
