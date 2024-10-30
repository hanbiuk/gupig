package com.gupig.user.infra.account.repo;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.repo.AccountRepository;
import com.gupig.user.infra.account.convertor.AccountConvertor;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataResult;
import com.gupig.user.infra.account.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 账号 资源库实现类
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountConvertor accountConvertor;

    /**
     * 根据业务线获取账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    @Override
    public AccountAggBO selectByBiz(AccountLogInCmd cmd) {
        AccountWithBizDataResult accountWithBizDataResult = accountMapper.selectByBiz(cmd);
        return accountConvertor.toAggBO(accountWithBizDataResult);
    }

}
