package com.gupig.user.app.account.service;

import com.gupig.user.client.account.api.AccountService;
import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.common.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 账号 服务实现类
 *
 * @author hanbiuk
 * @date 2024-10-14
 */
@Slf4j
@Service
@Validated
public class AccountServiceImpl implements AccountService {

    /**
     * 登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    @Override
    public Result<String> logIn(AccountLogInCmd cmd) {
        return Result.success("log in success");
    }

    /**
     * 登出
     *
     * @return 是否成功
     */
    @Override
    public Result<Boolean> logOut() {
        return Result.success(true);
    }

    /**
     * 注册
     *
     * @return 是否成功
     */
    @Override
    public Result<Boolean> signUp() {
        return Result.success(true);
    }

    /**
     * 注册并登陆
     *
     * @return 是否成功
     */
    @Override
    public Result<Boolean> signIn() {
        return Result.success(true);
    }

    /**
     * 销号
     *
     * @return 是否成功
     */
    @Override
    public Result<Boolean> signDown() {
        return Result.success(true);
    }

}
