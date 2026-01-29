package com.imis.petmanagebackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/page")
    public Result<?> getUserPage(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) Integer status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<User> pageInfo = userService.getUserPage(id, username, phone, role, status, page, pageSize);
        return Result.success(pageInfo); // 前端可以直接拿 records 和 total
    }

    @PutMapping("/toggleStatus")
    public Result<?> toggleUserStatus(@RequestParam Long id) {
        boolean flag = userService.toggleUserStatus(id);
        return Result.success(flag);
    }

    @PutMapping("/toggleRole")
    public Result<?> toggleUserRole(@RequestParam Long id, @RequestParam Integer role) {
        boolean flag = userService.toggleUserRole(id, role);
        return Result.success(flag);
    }

    /**
     * 更新用户信息（角色、地址）
     */
    @PutMapping("/update")
    public Result<?> updateUserInfo(@RequestBody User user) {
        if (user.getId() == null) {
            return Result.fail("用户ID不能为空");
        }
        // 只更新允许修改的字段
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setRole(user.getRole());
        updateUser.setAddress(user.getAddress());
        boolean flag = userService.updateById(updateUser);
        return flag ? Result.success("更新成功") : Result.fail("更新失败");
    }
}
