package com.gupig.user.infra.account.convertor;

import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataResult;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Objects;

/**
 * 账号对象 转换器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Component
public class AccountConvertor {

    @Resource
    private AccountBizConvertor accountBizConvertor;

    /**
     * 转换为 AccountAggBO
     *
     * @param dataResult 数据结果
     * @return AccountAggBO
     */
    public AccountAggBO toAggBO(AccountWithBizDataResult dataResult) {
        if (Objects.isNull(dataResult)) {
            return null;
        }

        AccountAggBO accountAggBO = new AccountAggBO();
        BeanUtils.copyProperties(dataResult, accountAggBO);

        AccountBizBO accountBizBO = accountBizConvertor.toBO(dataResult);
        accountAggBO.setAccountBizList(Collections.singletonList(accountBizBO));

        return accountAggBO;
    }

}
