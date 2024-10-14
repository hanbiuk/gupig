package com.gupig.user.adapter.account.web;

import com.gupig.user.client.common.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 账号 前端控制器
 *
 * @author hanbiuk
 * @date 2024-10-09
 */
@RestController
@RequestMapping("user/account")
public class AccountController {

    /**
     * 登陆
     *
     * @return 登陆凭证
     */
    @PostMapping("log/in")
    public Result<String> logIn() {
        return Result.success("login");
    }

    /**
     * 登出
     *
     * @return 是否成功
     */
    @PostMapping("log/out")
    public Result<Boolean> logOut() {
        return Result.success(true);
    }

    /**
     * 注册
     *
     * @return 是否成功
     */
    @PostMapping("sign/up")
    public Result<Boolean> signUp() {
        return Result.success(true);
    }

    /**
     * 注册并登陆
     *
     * @return 是否成功
     */
    @PostMapping("sign/in")
    public Result<Boolean> signIn() {
        return Result.success(true);
    }

    /**
     * 销户
     *
     * @return 是否成功
     */
    @PostMapping("sign/down")
    public Result<Boolean> signDown() {
        return Result.success(true);
    }

}