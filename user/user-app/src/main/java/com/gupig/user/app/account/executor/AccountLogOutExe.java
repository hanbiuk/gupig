package com.gupig.user.app.account.executor;

import cn.hutool.jwt.JWT;
import com.gupig.user.client.account.dto.AccountLogOutCmd;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.domain.account.entity.AccountLogoutBO;
import com.gupig.user.domain.account.repo.AccountLogoutRepository;
import com.gupig.user.infra.account.convertor.AccountLogoutConvertor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 账号登出 执行器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
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

        // 2. 验证token
        JWT jwt = JWT.of(cmd.getUserContext().getToken());
        if (jwt.verify()) {
            // 3. 新增登出记录
            AccountLogoutBO accountLogoutBO = accountLogoutConvertor.buildAddBO(cmd);
            Integer insert = accountLogoutRepository.add(accountLogoutBO);
            if (insert <= 0) {
                return Result.fail(ResultStatusEnum.SAVE_EXCEPTION);
            }
        }

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
