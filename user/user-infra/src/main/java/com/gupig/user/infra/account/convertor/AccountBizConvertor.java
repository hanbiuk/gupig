package com.gupig.user.infra.account.convertor;

import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataResult;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 账号业务线对象 转换器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Component
public class AccountBizConvertor {

    /**
     * 转换为 AccountBizBO
     *
     * @param dataResult 数据结果
     * @return AccountBizBO
     */
    public AccountBizBO toBO(AccountWithBizDataResult dataResult) {
        if (Objects.isNull(dataResult)) {
            return null;
        }

        AccountBizBO accountBizBO = new AccountBizBO();
        accountBizBO.setId(dataResult.getUabId());
        accountBizBO.setCode(dataResult.getUabCode());
        accountBizBO.setTenantCode(dataResult.getTenantCode());
        accountBizBO.setUaCode(dataResult.getCode());
        accountBizBO.setBizCode(dataResult.getBizCode());
        accountBizBO.setNickname(dataResult.getUabNickname());
        accountBizBO.setPortrait(dataResult.getUabPortrait());
        accountBizBO.setUserDetail(dataResult.getUabUserDetail());
        accountBizBO.setStatus(dataResult.getUabStatus());
        accountBizBO.setCstActivate(dataResult.getUabCstActivate());
        accountBizBO.setIsDeleted(dataResult.getUabIsDeleted());
        accountBizBO.setCreator(dataResult.getUabCreator());
        accountBizBO.setCstCreate(dataResult.getUabCstCreate());
        accountBizBO.setModifier(dataResult.getUabModifier());
        accountBizBO.setCstModify(dataResult.getUabCstModify());
        return accountBizBO;
    }

}
