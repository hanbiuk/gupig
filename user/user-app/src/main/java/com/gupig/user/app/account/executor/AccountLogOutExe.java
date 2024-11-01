package com.gupig.user.app.account.executor;

import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.domain.account.entity.AccountLogoutBO;
import com.gupig.user.domain.account.repo.AccountLogoutRepository;
import com.gupig.user.infra.account.convertor.AccountLogoutConvertor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 账号登出 执行器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Slf4j
@Component
public class AccountLogOutExe {

    @Resource
    private AccountLogoutRepository accountLogoutRepository;

    @Resource
    private AccountLogoutConvertor accountLogoutConvertor;

    /**
     * 登出
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    public Result<Boolean> execute(AccountLogOutCmd cmd) {
        // 1. 参数校验
        this.validate(cmd);

        // 2. 新增登出记录
        AccountLogoutBO accountLogoutBO = accountLogoutConvertor.buildAddBO(cmd);
        Integer addAccountLogout = accountLogoutRepository.add(accountLogoutBO);
        if (addAccountLogout <= 0) {
            log.error("AccountLogOutExe execute addAccountLogout exception, {}", addAccountLogout);
            return Result.fail(ResultStatusEnum.SAVE_EXCEPTION);
        }

        // 3. 删除过期的登出记录
        Integer deleteExpired = accountLogoutRepository.deleteExpired();
        log.info("AccountLogOutExe execute deleteExpired count: {}", deleteExpired);

        return Result.success(true);
    }

    /**
     * 参数校验
     *
     * @param cmd 参数
     */
    private void validate(AccountLogOutCmd cmd) {
        // no param to be validated
    }

}
