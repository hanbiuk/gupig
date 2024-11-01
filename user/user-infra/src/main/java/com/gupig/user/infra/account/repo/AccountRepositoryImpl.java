package com.gupig.user.infra.account.repo;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.entity.AccountBO;
import com.gupig.user.domain.account.repo.AccountRepository;
import com.gupig.user.infra.account.convertor.AccountConvertor;
import com.gupig.user.infra.account.dataobject.AccountDO;
import com.gupig.user.infra.account.dataquery.AccountDataQry;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataRes;
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
     * 获取含当前业务线的账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    @Override
    public AccountAggBO selectWithBiz(AccountLogInCmd cmd) {
        AccountDataQry dataQry = accountConvertor.toDataQry(cmd);
        AccountWithBizDataRes accountWithBizDataRes = accountMapper.selectWithBiz(dataQry);
        return accountConvertor.toAggBO(accountWithBizDataRes);
    }

    /**
     * 根据邮箱获取账号信息
     *
     * @param cmd 命令参数
     * @return 账号信息
     */
    @Override
    public AccountBO selectByEmail(AccountSignUpCmd cmd) {
        AccountDataQry dataQry = accountConvertor.toEmailDataQry(cmd);
        AccountDO accountDO = accountMapper.select(dataQry);
        return accountConvertor.toBO(accountDO);
    }

}
