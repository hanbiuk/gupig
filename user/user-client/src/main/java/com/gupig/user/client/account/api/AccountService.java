package com.gupig.user.client.account.api;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.client.account.dto.AccountSignInCmd;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.client.common.dto.Result;
import jakarta.validation.Valid;

/**
 * 账号 服务类
 *
 * @author hanbiuk
 * @date 2024-10-14
 */
public interface AccountService {

    /**
     * 登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    Result<String> logIn(@Valid AccountLogInCmd cmd);

    /**
     * 登出
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    Result<Boolean> logOut(@Valid AccountLogOutCmd cmd);

    /**
     * 注册
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    Result<Boolean> signUp(@Valid AccountSignUpCmd cmd);

    /**
     * 注册并登陆
     *
     * @param cmd 命令参数
     * @return 登陆凭证
     */
    Result<String> signIn(@Valid AccountSignInCmd cmd);

    /**
     * 销号
     *
     * @return 是否成功
     */
    Result<Boolean> signDown();

}
