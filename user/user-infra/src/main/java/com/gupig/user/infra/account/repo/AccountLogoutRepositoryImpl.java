package com.gupig.user.infra.account.repo;

import com.gupig.user.client.common.dto.UserContextDTO;
import com.gupig.user.domain.account.entity.AccountLogoutBO;
import com.gupig.user.domain.account.repo.AccountLogoutRepository;
import com.gupig.user.infra.account.convertor.AccountLogoutConvertor;
import com.gupig.user.infra.account.dataobject.AccountLogoutDO;
import com.gupig.user.infra.account.dataquery.AccountLogoutDataQry;
import com.gupig.user.infra.account.mapper.AccountLogoutMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 账号登出 资源库实现类
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Repository
public class AccountLogoutRepositoryImpl implements AccountLogoutRepository {

    @Resource
    private AccountLogoutMapper accountLogoutMapper;

    @Resource
    private AccountLogoutConvertor accountLogoutConvertor;

    /**
     * 新增记录
     *
     * @param accountLogoutBO 登出信息
     * @return 受影响行数
     */
    @Override
    public Integer add(AccountLogoutBO accountLogoutBO) {
        AccountLogoutDO accountLogoutDO = accountLogoutConvertor.toDO(accountLogoutBO);
        return accountLogoutMapper.insertSelective(accountLogoutDO);
    }

    /**
     * 是否已登出
     *
     * @param userContext 用户上下文
     * @return true: 已登出, false: 未登出
     */
    @Override
    public Boolean hasLogout(UserContextDTO userContext) {
        AccountLogoutDataQry dataQry = accountLogoutConvertor.toDataQry(userContext);
        List<AccountLogoutDO> accountLogoutDOList = accountLogoutMapper.selectList(dataQry);
        return accountLogoutDOList.stream().map(AccountLogoutDO::getToken).anyMatch(userContext.getToken()::equals);
    }

}
