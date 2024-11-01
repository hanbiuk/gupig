package com.gupig.user.infra.account.convertor;

import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.client.account.enums.AccountStatusEnum;
import com.gupig.user.domain.account.entity.AccountBO;
import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.infra.account.dataobject.AccountBizDO;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataRes;
import com.gupig.user.infra.account.dataquery.AccountBizDataQry;
import com.gupig.user.infra.common.until.SnowflakeCodeWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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
    public AccountBizBO toBO(AccountWithBizDataRes dataResult) {
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
        accountBizBO.setIsDeleted(dataResult.getUabIsDeleted());
        accountBizBO.setCreator(dataResult.getUabCreator());
        accountBizBO.setCstCreate(dataResult.getUabCstCreate());
        accountBizBO.setModifier(dataResult.getUabModifier());
        accountBizBO.setCstModify(dataResult.getUabCstModify());
        return accountBizBO;
    }

    /**
     * 转换为 AccountBizBO
     *
     * @param accountBizDO 数据对象
     * @return AccountBizBO
     */
    public AccountBizBO toBO(AccountBizDO accountBizDO) {
        if (Objects.isNull(accountBizDO)) {
            return null;
        }

        AccountBizBO accountBizBO = new AccountBizBO();
        BeanUtils.copyProperties(accountBizDO, accountBizBO);
        return accountBizBO;
    }

    /**
     * 构建 AccountBizBO
     *
     * @param accountBO 账号信息
     * @param cmd       命令参数
     * @return AccountBizBO
     */
    public AccountBizBO buildAddBO(AccountBO accountBO, AccountSignUpCmd cmd) {
        AccountBizBO accountBizBO = new AccountBizBO();
        BeanUtils.copyProperties(accountBO, accountBizBO);

        accountBizBO.setCode(SnowflakeCodeWorker.getInstance().nextId("UAB"));

        accountBizBO.setBizCode(cmd.getBizCode());

        accountBizBO.setStatus(AccountStatusEnum.ENABLE.getCode());

        LocalDateTime now = LocalDateTime.now();
        accountBizBO.setCreator(accountBO.getCreator());
        accountBizBO.setCstCreate(now);
        accountBizBO.setModifier(accountBO.getCreator());
        accountBizBO.setCstModify(now);

        return accountBizBO;
    }

    /**
     * 转换为 AccountBizDO
     *
     * @param accountBizBO 业务对象
     * @return AccountBizDO
     */
    public AccountBizDO toDO(AccountBizBO accountBizBO) {
        if (Objects.isNull(accountBizBO)) {
            return null;
        }

        AccountBizDO accountBizDO = new AccountBizDO();
        BeanUtils.copyProperties(accountBizBO, accountBizDO);

        return accountBizDO;
    }

    /**
     * 构建 AccountBizBO
     *
     * @param accountBO 账号信息
     * @param cmd       命令参数
     * @return AccountBizBO
     */
    public AccountBizBO buildQryBO(AccountBO accountBO, AccountSignUpCmd cmd) {
        AccountBizBO accountBizBO = new AccountBizBO();
        accountBizBO.setTenantCode(accountBO.getTenantCode());
        accountBizBO.setBizCode(cmd.getBizCode());
        accountBizBO.setUaCode(accountBO.getCode());
        return accountBizBO;
    }

    /**
     * 转换为 AccountBizDataQry
     *
     * @param accountBizBO 业务对象
     * @return AccountBizDataQry
     */
    public AccountBizDataQry toDataQry(AccountBizBO accountBizBO) {
        if (Objects.isNull(accountBizBO)) {
            return null;
        }

        AccountBizDataQry accountBizDataQry = new AccountBizDataQry();
        BeanUtils.copyProperties(accountBizBO, accountBizDataQry);
        return accountBizDataQry;
    }

}
