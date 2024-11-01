package com.gupig.user.domain.account.repo;

import com.gupig.user.client.common.dto.UserContextDTO;
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

    /**
     * 删除过期记录
     *
     * @return 受影响行数
     */
    Integer deleteExpired();

    /**
     * 是否已登出
     *
     * @param userContext 用户上下文
     * @return true: 已登出, false: 未登出
     */
    Boolean hasLogout(UserContextDTO userContext);

}
