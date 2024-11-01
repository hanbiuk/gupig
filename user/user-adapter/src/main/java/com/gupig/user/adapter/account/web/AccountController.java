package com.gupig.user.adapter.account.web;

import com.gupig.user.client.account.api.AccountService;
import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.client.common.dto.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Resource
    private AccountService accountService;

    /**
     * 登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    @PostMapping("log/in")
    public Result<String> logIn(@RequestBody AccountLogInCmd cmd) {
        return accountService.logIn(cmd);
    }

    /**
     * 登出
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    @PostMapping("log/out")
    public Result<Boolean> logOut(@RequestBody AccountLogOutCmd cmd) {
        return accountService.logOut(cmd);
    }

    /**
     * 注册
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    @PostMapping("sign/up")
    public Result<Boolean> signUp(@RequestBody AccountSignUpCmd cmd) {
        return accountService.signUp(cmd);
    }

    /**
     * 注册并登陆
     *
     * @return 是否成功
     */
    @PostMapping("sign/in")
    public Result<Boolean> signIn() {
        return accountService.signIn();
    }

    /**
     * 销号
     *
     * @return 是否成功
     */
    @PostMapping("sign/down")
    public Result<Boolean> signDown() {
        return accountService.signDown();
    }

}