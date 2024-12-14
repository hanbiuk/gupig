package com.gupig.user.infra.account.convertor;

import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.client.common.dto.UserContextDTO;
import com.gupig.user.domain.account.entity.AccountLogoutBO;
import com.gupig.user.infra.account.dataobject.AccountLogoutDO;
import com.gupig.user.infra.account.dataquery.AccountLogoutDataQry;
import com.gupig.user.infra.common.until.SnowflakeCodeWorker;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

        accountLogoutBO.setCode(SnowflakeCodeWorker.getInstance().nextId("UAL"));

        accountLogoutBO.setTenantCode(cmd.getUserContext().getOptTenantCode());
        accountLogoutBO.setUaCode(cmd.getUserContext().getOptUaCode());
        accountLogoutBO.setBizCode(cmd.getUserContext().getOptBizCode());
        accountLogoutBO.setToken(cmd.getUserContext().getToken());
        accountLogoutBO.setCstExpire(cmd.getUserContext().getCstExpire());

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        accountLogoutBO.setCreator(cmd.getUserContext().getOptUaCode());
        accountLogoutBO.setCstCreate(now);
        accountLogoutBO.setModifier(cmd.getUserContext().getOptUaCode());
        accountLogoutBO.setCstModify(now);

        return accountLogoutBO;
    }

    /**
     * 转换为 AccountLogoutDO
     *
     * @param accountLogoutBO 业务对象
     * @return AccountLogoutDO
     */
    public AccountLogoutDO toDO(AccountLogoutBO accountLogoutBO) {
        if (Objects.isNull(accountLogoutBO)) {
            return null;
        }

        AccountLogoutDO accountLogoutDO = new AccountLogoutDO();
        BeanUtils.copyProperties(accountLogoutBO, accountLogoutDO);

        return accountLogoutDO;
    }

    /**
     * 转换为 AccountLogoutDataQry
     *
     * @param userContext 用户上下文
     * @return AccountLogoutDataQry
     */
    public AccountLogoutDataQry toDataQry(UserContextDTO userContext) {
        if (Objects.isNull(userContext)) {
            return null;
        }

        AccountLogoutDataQry accountLogoutDataQry = new AccountLogoutDataQry();
        accountLogoutDataQry.setTenantCode(userContext.getOptTenantCode());
        accountLogoutDataQry.setBizCode(userContext.getOptBizCode());
        accountLogoutDataQry.setUaCode(userContext.getOptUaCode());

        return accountLogoutDataQry;
    }

}
