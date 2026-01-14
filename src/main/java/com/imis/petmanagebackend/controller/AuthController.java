package com.imis.petmanagebackend.controller;

import com.imis.petmanagebackend.common.Result;
import com.imis.petmanagebackend.entity.LoginVO;
import com.imis.petmanagebackend.entity.User;
import com.imis.petmanagebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
@Slf4j
public class AuthController {

    @Autowired
    private  UserService userService;
    /**
     * 用户登录
     *
     * @param
     * @return 登录结果
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestParam("account") String account, @RequestParam("password") String password) {
        LoginVO vo = userService.login(account, password);
        return Result.success(vo);
    }

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<Boolean> register(@RequestBody User user) {
        boolean result = userService.register(user);
        return Result.success(result);
    }
    // 退出登录
    @PostMapping("/logout")
    public Result<String> logout() {
        // JWT 无状态，服务器无需处理，前端删除 token 即可
        return Result.success("退出成功");
    }

}
