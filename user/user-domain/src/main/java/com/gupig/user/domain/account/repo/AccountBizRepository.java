package com.gupig.user.domain.account.repo;

import com.gupig.user.domain.account.entity.AccountBizBO;

/**
 * 账号业务线 资源库类
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
public interface AccountBizRepository {

    /**
     * 获取账号业务线信息
     *
     * @param accountBizBO 业务对象
     * @return 账号业务线信息
     */
    AccountBizBO select(AccountBizBO accountBizBO);

    /**
     * 新增记录
     *
     * @param accountBizBO 账号业务线信息
     * @return 受影响行数
     */
    Integer add(AccountBizBO accountBizBO);

    /**
     * 更新状态
     *
     * @param accountBizBO 账号业务线信息
     * @return 受影响行数
     */
    Integer updateStatus(AccountBizBO accountBizBO);

}
