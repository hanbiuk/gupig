package com.gupig.user.domain.account.repo;

import com.gupig.user.client.account.dto.AccountLogInQry;
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
     * @param qry 查询参数
     * @return 账号信息
     */
    AccountAggBO selectByBiz(AccountLogInQry qry);

    /**
     * 生成token
     *
     * @param accountAggBO 账号信息
     * @return token
     */
    String generateToken(AccountAggBO accountAggBO);

}
