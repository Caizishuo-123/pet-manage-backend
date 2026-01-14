package com.imis.petmanagebackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.imis.petmanagebackend.common.BusinessException;
import com.imis.petmanagebackend.entity.LoginVO;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.UserService;
import com.imis.petmanagebackend.mapper.UserMapper;
import com.imis.petmanagebackend.utils.JwtUtil;
import com.imis.petmanagebackend.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
* @author 64360
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2026-01-11 21:07:15
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginVO login(String account, String password) {
        // 查询用户，用户名或手机号都可以
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, account)
                .or()
                .eq(User::getPhone, account);

        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        // 密码校验
        if (!MD5Util.matches(password, user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        // 密码校验
        if (user.getRole() == 1) {
            throw new BusinessException("非管理员登录,无法进入");
        }
        // 生成 token
        // 组装返回对象
        LoginVO vo = new LoginVO();
        vo.setToken(JwtUtil.generateToken(user.getUsername()));
        vo.setUserId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setPhone(maskPhone(user.getPhone()));
        vo.setRole(user.getRole());
        return vo;
    }

    @Override
    public boolean register(User user) {
        // 检查账号是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(User::getPhone, user.getPhone());
        if (userMapper.selectCount(lambdaQueryWrapper1) > 0){
            throw new BusinessException("手机号已存在");
        }
        // 检查用户名是否存在
        LambdaQueryWrapper<User> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(User::getUsername, user.getUsername());
        if (userMapper.selectCount(lambdaQueryWrapper2) > 0){
            throw new BusinessException("用户名已存在");
        }
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        // 随机分配头像
        user.setAvatar(getRandomAvatar());
        return userMapper.insert(user) > 0;
    }

    @Override
    public Page<User> getUserPage(Long id, String username, String phone, Integer page, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (id != null) queryWrapper.eq(User::getId, id);
        if (username != null && !username.isEmpty()) queryWrapper.like(User::getUsername, username);
        if (phone != null && !phone.isEmpty()) queryWrapper.like(User::getPhone, phone);
        queryWrapper.orderByAsc(User::getId);

        // page = 当前页，pageSize = 每页条数
        Page<User> pageInfo = new Page<>(page, pageSize);
        return userMapper.selectPage(pageInfo, queryWrapper);
    }

    @Override
    public boolean toggleUserStatus(Long id) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, id);
        User user = new User();
        if (userMapper.selectCount(lambdaQueryWrapper) > 0) user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null) {
            if(user.getStatus() == 1) {
                user.setStatus(0);
                return userMapper.updateById(user) > 0;
            }
            if(user.getStatus() == 0) {
                user.setStatus(1);
                return userMapper.updateById(user) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean toggleUserRole(Long id,Integer role) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId, id);
        User user = new User();
        if (userMapper.selectCount(lambdaQueryWrapper) > 0) user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null) {
            user.setRole(role);
            return userMapper.updateById(user) > 0;
        }
        return false;
    }

    /**
     * 随机获取头像路径
     */
    private String getRandomAvatar() {
        // 头像编号 1-3
        int index = new Random().nextInt(3) + 1; // 1~3
        // 返回数据库存储的路径，前端访问即可
        return "/img/" + index + ".jpg";
    }

    /**
     * 隐藏手机号
     * @param phone
     * @return String
     */
    private String maskPhone(String phone) {
        if (phone == null || phone.length() != 11) {
            return phone;
        }
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }
}




