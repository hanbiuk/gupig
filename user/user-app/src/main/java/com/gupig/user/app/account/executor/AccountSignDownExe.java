package com.gupig.user.app.account.executor;

import com.gupig.user.client.account.dto.AccountSignDownCmd;
import com.gupig.user.client.account.enums.AccountStatusEnum;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.domain.account.repo.AccountBizRepository;
import com.gupig.user.infra.account.convertor.AccountBizConvertor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 账号销号 执行器
 *
 * @author hanbiuk
 * @date 2024-11-01
 */
@Slf4j
@Component
public class AccountSignDownExe {

    @Resource
    private AccountBizRepository accountBizRepository;

    @Resource
    private AccountBizConvertor accountBizConvertor;

    /**
     * 销号
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    public Result<Boolean> execute(AccountSignDownCmd cmd) {
        // 1. 参数校验
        this.validate(cmd);

        // 2. 验证码是否通过

        // 3. 账号是否可销号
        AccountBizBO accountBizQryBO = accountBizConvertor.buildQryBO(cmd);
        AccountBizBO accountBizBO = accountBizRepository.select(accountBizQryBO);
        if (Objects.equals(AccountStatusEnum.SIGN_DOWN.getCode(), accountBizBO.getStatus())) {
            return Result.success("账号已销号", true);
        }

        // 4. 注销业务线账号
        AccountBizBO accountStatusBizBO = accountBizConvertor.buildStatusBO(accountBizBO, cmd);
        Integer updateStatus = accountBizRepository.updateStatus(accountStatusBizBO);

        if (updateStatus <= 0) {
            log.error("AccountSignDownExe execute updateStatus exception, {}", updateStatus);
            return Result.fail(ResultStatusEnum.UPDATE_EXCEPTION);
        }

        // 5. 清除业务线权限

        return Result.success(true);
    }

    /**
     * 参数校验
     *
     * @param cmd 参数
     */
    private void validate(AccountSignDownCmd cmd) {
        // no param to be validated
    }

}
