package com.gupig.user.domain.account.repo;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.entity.AccountBO;

/**
 * 账号 资源库类
 *
 * @author hanbiuk
 * @date 2024-10-25
 */
public interface AccountRepository {

    /**
     * 获取含当前业务线的账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    AccountAggBO selectWithBiz(AccountLogInCmd cmd);

    /**
     * 根据邮箱获取账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    AccountBO selectByEmail(AccountSignUpCmd cmd);

}
