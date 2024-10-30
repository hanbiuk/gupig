package com.gupig.user.infra.account.convertor;

import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.domain.account.entity.AccountLogoutBO;
import com.gupig.user.infra.account.dataobject.AccountLogoutDO;
import com.gupig.user.infra.common.until.SnowflakeCodeWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 账号登出对象 转换器
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Component
public class AccountLogoutConvertor {

    /**
     * 构建 AccountLogoutBO
     *
     * @param cmd 命令参数
     * @return AccountLogoutBO
     */
    public AccountLogoutBO buildAddBO(AccountLogOutCmd cmd) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        AccountLogoutBO accountLogoutBO = new AccountLogoutBO();
        BeanUtils.copyProperties(cmd, accountLogoutBO);

        LocalDateTime now = LocalDateTime.now();
        accountLogoutBO.setCreator(accountLogoutBO.getUaCode());
        accountLogoutBO.setCstCreate(now);
        accountLogoutBO.setModifier(accountLogoutBO.getUaCode());
        accountLogoutBO.setCstModify(now);

        return accountLogoutBO;
    }

    /**
     * 转换为 AccountLogoutDO
     *
     * @param accountLogoutBO 业务对象
     * @return AccountLogoutDO
     */
    public AccountLogoutDO toAddDO(AccountLogoutBO accountLogoutBO) {
        if (Objects.isNull(accountLogoutBO)) {
            return null;
        }

        AccountLogoutDO accountLogoutDO = new AccountLogoutDO();
        BeanUtils.copyProperties(accountLogoutBO, accountLogoutDO);

        accountLogoutDO.setCode(SnowflakeCodeWorker.getInstance().nextId("UAL"));

        return accountLogoutDO;
    }

}
