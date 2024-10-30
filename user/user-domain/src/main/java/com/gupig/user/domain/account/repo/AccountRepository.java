package com.gupig.user.domain.account.repo;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.domain.account.aggregate.AccountAggBO;

/**
 * 账号 资源库类
 *
 * @author hanbiuk
 * @date 2024-10-25
 */
public interface AccountRepository {

    /**
     * 根据业务线获取账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    AccountAggBO selectByBiz(AccountLogInCmd cmd);

}
