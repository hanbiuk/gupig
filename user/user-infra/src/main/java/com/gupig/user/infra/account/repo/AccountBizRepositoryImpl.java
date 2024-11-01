package com.gupig.user.infra.account.repo;

import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.domain.account.repo.AccountBizRepository;
import com.gupig.user.infra.account.convertor.AccountBizConvertor;
import com.gupig.user.infra.account.dataobject.AccountBizDO;
import com.gupig.user.infra.account.dataquery.AccountBizDataQry;
import com.gupig.user.infra.account.mapper.AccountBizMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * 账号业务线 资源库实现类
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
@Repository
public class AccountBizRepositoryImpl implements AccountBizRepository {

    @Resource
    private AccountBizMapper accountBizMapper;

    @Resource
    private AccountBizConvertor accountBizConvertor;

    /**
     * 获取账号业务线信息
     *
     * @param accountBizBO 业务对象
     * @return 账号业务线信息
     */
    @Override
    public AccountBizBO select(AccountBizBO accountBizBO) {
        AccountBizDataQry dataQry = accountBizConvertor.toDataQry(accountBizBO);
        AccountBizDO accountBizDO = accountBizMapper.select(dataQry);
        return accountBizConvertor.toBO(accountBizDO);
    }

    /**
     * 新增记录
     *
     * @param accountBizBO 账号业务线信息
     * @return 受影响行数
     */
    @Override
    public Integer add(AccountBizBO accountBizBO) {
        AccountBizDO accountBizDO = accountBizConvertor.toAddDO(accountBizBO);
        return accountBizMapper.insertSelective(accountBizDO);
    }

}
