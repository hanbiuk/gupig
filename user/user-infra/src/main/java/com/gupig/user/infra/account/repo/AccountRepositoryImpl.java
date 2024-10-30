package com.gupig.user.infra.account.repo;

import cn.hutool.jwt.JWT;
import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.repo.AccountRepository;
import com.gupig.user.infra.account.config.TokenProperties;
import com.gupig.user.infra.account.convertor.AccountConvertor;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataResult;
import com.gupig.user.infra.account.mapper.AccountMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private TokenProperties tokenProperties;

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

    /**
     * 生成token
     *
     * @param accountAggBO 账号信息
     * @return token
     */
    @Override
    public String generateToken(AccountAggBO accountAggBO) {
        Map<String, String> payloads = new HashMap<>(8);
        payloads.put("tenantCode", accountAggBO.getTenantCode());
        payloads.put("bizCode", accountAggBO.getAccountBizList().get(0).getBizCode());
        payloads.put("uaCode", accountAggBO.getCode());
        payloads.put("username", accountAggBO.getUsername());
        payloads.put("nickname", accountAggBO.getAccountBizList().get(0).getNickname());
        return JWT.create()
                .addPayloads(payloads)
                .setKey(tokenProperties.getKey().getBytes())
                .setExpiresAt(new Date(System.currentTimeMillis() + tokenProperties.getExpireTime()))
                .sign();
    }

}
