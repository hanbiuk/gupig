package com.gupig.user.app.account.executor;

import cn.hutool.crypto.digest.DigestUtil;
import com.gupig.user.client.account.dto.AccountSignUpCmd;
import com.gupig.user.client.account.enums.AccountStatusEnum;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.domain.account.entity.AccountBO;
import com.gupig.user.domain.account.entity.AccountBizBO;
import com.gupig.user.domain.account.repo.AccountBizRepository;
import com.gupig.user.domain.account.repo.AccountRepository;
import com.gupig.user.infra.account.convertor.AccountBizConvertor;
import com.gupig.user.infra.account.convertor.AccountConvertor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;

/**
 * 账号注册 执行器
 *
 * @author hanbiuk
 * @date 2024-10-31
 */
@Slf4j
@Component
public class AccountSignUpExe {

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private AccountRepository accountRepository;
    @Resource
    private AccountBizRepository accountBizRepository;

    @Resource
    private AccountBizConvertor accountBizConvertor;
    @Resource
    private AccountConvertor accountConvertor;

    /**
     * 注册
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    public Result<Boolean> execute(AccountSignUpCmd cmd) {
        // 1. 参数校验
        this.validate(cmd);

        // 2. 验证码是否通过

        // 3. 邮箱是否已注册
        AccountBO accountBO = accountRepository.selectByEmail(cmd);
        // 3.1. 处理邮箱已注册的场景
        if (Objects.nonNull(accountBO)) {
            return this.dealExistEmail(cmd, accountBO);
        }
        // 3.2. 处理邮箱未注册的场景
        else {
            return this.dealNotExistEmail(cmd);
        }
    }

    /**
     * 参数校验
     *
     * @param cmd 参数
     */
    private void validate(AccountSignUpCmd cmd) {
        // no param to be validated
    }

    /**
     * 处理邮箱已注册的场景
     *
     * @param cmd       命令参数
     * @param accountBO 账号信息
     * @return 处理结果
     */
    private Result<Boolean> dealExistEmail(AccountSignUpCmd cmd, AccountBO accountBO) {
        // 3.1. 邮箱已注册, 账户非启用状态
        if (!Objects.equals(AccountStatusEnum.ENABLE.getCode(), accountBO.getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_STATUS_NOT_ENABLE,
                    "邮箱已注册, 账号已" + AccountStatusEnum.of(accountBO.getStatus()).getDesc());
        }

        // 3.2. 邮箱已注册, 业务线已注册, 账户非启用状态
        AccountBizBO accountBizQryBO = accountBizConvertor.buildQryBO(accountBO, cmd);
        AccountBizBO accountBizBO = accountBizRepository.select(accountBizQryBO);
        if (Objects.nonNull(accountBizBO)
                && !Objects.equals(AccountStatusEnum.ENABLE.getCode(), accountBizBO.getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_STATUS_NOT_ENABLE,
                    "邮箱已注册, 账号已" + AccountStatusEnum.of(accountBizBO.getStatus()).getDesc());
        }
        // 3.2. 邮箱已注册, 业务线已注册, 账户启用状态
        else if (Objects.nonNull(accountBizBO)
                && Objects.equals(AccountStatusEnum.ENABLE.getCode(), accountBizBO.getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_EMAIL_SIGNED_UP);
        }
        // 3.3. 邮箱已注册, 业务线未注册
        else {
            // 3.3.1. 验证密码
            String passwordDigest = DigestUtil.md5Hex(cmd.getPassword() + accountBO.getSalt());
            if (!Objects.equals(accountBO.getPassword(), passwordDigest)) {
                return Result.fail(ResultStatusEnum.ACCOUNT_EMAIL_SIGNED_UP_PASSWORD_ERROR,
                        ResultStatusEnum.ACCOUNT_EMAIL_SIGNED_UP_PASSWORD_ERROR.getMsg() + ", 无法关联该业务线");
            }

            // 3.3.2. 新增账号业务线记录
            AccountBizBO accountBizAddBO = accountBizConvertor.buildAddBO(accountBO, cmd);
            Integer addAccountBiz = accountBizRepository.add(accountBizAddBO);

            // 3.3.2. 返回结果
            if (addAccountBiz <= 0) {
                log.error("AccountSignUpExe execute addAccountBiz exception, {}", addAccountBiz);
                return Result.fail(ResultStatusEnum.SAVE_EXCEPTION);
            } else if (!Objects.equals(accountBO.getUsername(), cmd.getUsername())) {
                return Result.success(ResultStatusEnum.ACCOUNT_EMAIL_SIGNED_UP.getMsg() + ", 用户名为: " + accountBO.getUsername(), true);
            } else {
                return Result.success(true);
            }
        }
    }

    /**
     * 处理邮箱未注册的场景
     *
     * @param cmd 命令参数
     * @return 处理结果
     */
    private Result<Boolean> dealNotExistEmail(AccountSignUpCmd cmd) {
        // 1. 用户名是否已存在
        AccountBO accountBO = accountRepository.selectByName(cmd);
        if (Objects.nonNull(accountBO)) {
            return Result.fail(ResultStatusEnum.ACCOUNT_USERNAME_EXIST);
        }

        Boolean executed = transactionTemplate.execute(status -> {
            try {
                // 2. 新增账号记录
                AccountBO accountAddBO = accountConvertor.buildAddBO(cmd);
                Integer addAccount = accountRepository.add(accountAddBO);
                if (addAccount <= 0) {
                    status.setRollbackOnly();
                    log.error("AccountSignUpExe dealNotExistEmail addAccount exception, {}", addAccount);
                    return false;
                }

                // 3. 新增账号业务线记录
                AccountBizBO accountBizAddBO = accountBizConvertor.buildAddBO(accountAddBO, cmd);
                Integer addAccountBiz = accountBizRepository.add(accountBizAddBO);
                if (addAccountBiz <= 0) {
                    status.setRollbackOnly();
                    log.error("AccountSignUpExe dealNotExistEmail addAccountBiz exception, {}", addAccountBiz);
                    return false;
                }

                return true;
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("AccountSignUpExe dealNotExistEmail execute exception", e);
                return false;
            }
        });

        if (Boolean.TRUE.equals(executed)) {
            return Result.fail(ResultStatusEnum.SAVE_EXCEPTION);
        } else {
            return Result.success(true);
        }
    }

}
