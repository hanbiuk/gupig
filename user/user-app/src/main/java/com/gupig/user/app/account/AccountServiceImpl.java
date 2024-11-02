package com.gupig.user.app.account;

import com.gupig.user.app.account.executor.AccountLogInExe;
import com.gupig.user.app.account.executor.AccountLogOutExe;
import com.gupig.user.app.account.executor.AccountSignDownExe;
import com.gupig.user.app.account.executor.AccountSignUpExe;
import com.gupig.user.client.account.api.AccountService;
import com.gupig.user.client.account.dto.*;
import com.gupig.user.client.common.dto.Result;
import jakarta.annotation.Resource;
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

    @Resource
    private AccountLogInExe accountLogInExe;
    @Resource
    private AccountLogOutExe accountLogOutExe;
    @Resource
    private AccountSignUpExe accountSignUpExe;
    @Resource
    private AccountSignDownExe accountSignDownExe;

    /**
     * 登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    @Override
    public Result<String> logIn(AccountLogInCmd cmd) {
        return accountLogInExe.execute(cmd);
    }

    /**
     * 登出
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    @Override
    public Result<Boolean> logOut(AccountLogOutCmd cmd) {
        return accountLogOutExe.execute(cmd);
    }

    /**
     * 注册
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    @Override
    public Result<Boolean> signUp(AccountSignUpCmd cmd) {
        return accountSignUpExe.execute(cmd);
    }

    /**
     * 注册并登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    @Override
    public Result<String> signIn(AccountSignInCmd cmd) {
        return accountSignUpExe.executeAndIn(cmd);
    }

    /**
     * 销号
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    @Override
    public Result<Boolean> signDown(AccountSignDownCmd cmd) {
        return accountSignDownExe.execute(cmd);
    }

}
