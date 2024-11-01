package com.gupig.user.infra.account.convertor;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.jwt.JWT;
import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.client.account.enums.AccountStatusEnum;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.entity.AccountBO;
import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.infra.account.config.TokenProperties;
import com.gupig.user.infra.account.dataobject.AccountDO;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataRes;
import com.gupig.user.infra.account.dataquery.AccountDataQry;
import com.gupig.user.infra.common.until.SnowflakeCodeWorker;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

    @Resource
    private TokenProperties tokenProperties;

    /**
     * 生成token
     *
     * @param accountAggBO 账号信息
     * @return token
     */
    public String buildToken(AccountAggBO accountAggBO) {
        Map<String, Object> payloads = new HashMap<>(8);
        payloads.put("tenantCode", accountAggBO.getTenantCode());
        payloads.put("bizCode", accountAggBO.getAccountBizList().get(0).getBizCode());
        payloads.put("uaCode", accountAggBO.getCode());
        payloads.put("username", accountAggBO.getUsername());
        payloads.put("nickname", accountAggBO.getAccountBizList().get(0).getNickname());
        payloads.put("cstExpire", LocalDateTime.now().plusDays(tokenProperties.getExpireDays()));
        return JWT.create()
                .addPayloads(payloads)
                .setKey(tokenProperties.getKey().getBytes())
                .sign();
    }

    /**
     * 转换为 AccountAggBO
     *
     * @param dataResult 数据结果
     * @return AccountAggBO
     */
    public AccountAggBO toAggBO(AccountWithBizDataRes dataResult) {
        if (Objects.isNull(dataResult)) {
            return null;
        }

        AccountAggBO accountAggBO = new AccountAggBO();
        BeanUtils.copyProperties(dataResult, accountAggBO);

        AccountBizBO accountBizBO = accountBizConvertor.toBO(dataResult);
        accountAggBO.setAccountBizList(Collections.singletonList(accountBizBO));

        return accountAggBO;
    }

    /**
     * 转换为 AccountBO
     *
     * @param accountDO 数据对象
     * @return AccountBO
     */
    public AccountBO toBO(AccountDO accountDO) {
        if (Objects.isNull(accountDO)) {
            return null;
        }

        AccountBO accountBO = new AccountBO();
        BeanUtils.copyProperties(accountDO, accountBO);

        return accountBO;
    }

    /**
     * 转换为 AccountDataQry
     *
     * @param cmd 命令参数
     * @return AccountDataQry
     */
    public AccountDataQry toDataQry(AccountLogInCmd cmd) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        AccountDataQry dataQry = new AccountDataQry();
        BeanUtils.copyProperties(cmd, dataQry);
        return dataQry;
    }

    /**
     * 转换为 AccountDataQry
     *
     * @param cmd 命令参数
     * @return AccountDataQry
     */
    public AccountDataQry toEmailDataQry(AccountSignUpCmd cmd) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        AccountDataQry dataQry = new AccountDataQry();
        BeanUtils.copyProperties(cmd, dataQry);

        dataQry.setUsername(null);
        return dataQry;
    }

    /**
     * 转换为 AccountDataQry
     *
     * @param cmd 命令参数
     * @return AccountDataQry
     */
    public AccountDataQry toNameDataQry(AccountSignUpCmd cmd) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        AccountDataQry dataQry = new AccountDataQry();
        BeanUtils.copyProperties(cmd, dataQry);

        dataQry.setEmail(null);
        return dataQry;
    }

    /**
     * 构建 AccountBO
     *
     * @param cmd 命令参数
     * @return AccountBO
     */
    public AccountBO buildAddBO(AccountSignUpCmd cmd) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        AccountBO accountBO = new AccountBO();
        BeanUtils.copyProperties(cmd, accountBO);

        String uaCode = SnowflakeCodeWorker.getInstance().nextId("UA");
        accountBO.setCode(uaCode);

        String salt = RandomUtil.randomString(6);
        accountBO.setSalt(salt);
        String passwordDigest = DigestUtil.md5Hex(cmd.getPassword() + salt);
        accountBO.setPassword(passwordDigest);

        accountBO.setStatus(AccountStatusEnum.ENABLE.getCode());

        LocalDateTime now = LocalDateTime.now();
        accountBO.setCreator(uaCode);
        accountBO.setCstCreate(now);
        accountBO.setModifier(uaCode);
        accountBO.setCstModify(now);

        return accountBO;
    }

    /**
     * 转换为 AccountDO
     *
     * @param accountBO 业务对象
     * @return AccountDO
     */
    public AccountDO toDO(AccountBO accountBO) {
        if (Objects.isNull(accountBO)) {
            return null;
        }

        AccountDO accountDO = new AccountDO();
        BeanUtils.copyProperties(accountBO, accountDO);

        return accountDO;
    }

}
