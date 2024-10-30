package com.gupig.user.domain.account.repo;

import com.gupig.user.domain.account.entity.AccountLogoutBO;

/**
 * 账号登出 资源库类
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
public interface AccountLogoutRepository {

    /**
     * 新增记录
     *
     * @param accountLogoutBO 登出信息
     * @return 受影响行数
     */
    Integer add(AccountLogoutBO accountLogoutBO);

}
