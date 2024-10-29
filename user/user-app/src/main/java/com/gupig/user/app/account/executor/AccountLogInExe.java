package com.gupig.user.app.account.executor;

import cn.hutool.core.util.StrUtil;
import com.gupig.user.client.account.dto.AccountLogInQry;
import com.gupig.user.client.account.enums.AccountStatusEnum;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.domain.account.aggregate.AccountAggBO;
import com.gupig.user.domain.account.repo.AccountRepository;
import com.gupig.user.infra.common.until.Assert;
import com.gupig.user.infra.common.until.Digest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 账号登陆 执行器
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
@Component
public class AccountLogInExe {

    @Resource
    private AccountRepository accountRepository;

    /**
     * 登陆
     *
     * @param qry 查询参数
     * @return 登陆凭证
     */
    public Result<String> execute(AccountLogInQry qry) {
        // 1. 参数校验
        this.validate(qry);

        // 2. 获取业务线的账号
        AccountAggBO accountAggBO = accountRepository.selectByBiz(qry);

        // 3. 账号不存在, 直接返回
        if (Objects.isNull(accountAggBO)) {
            return Result.fail(ResultStatusEnum.DATA_NOT_EXIST, "user or verify error");
        }

        // 4. 验证登陆
        // 4.1. 如果是密码登陆, 校验密码
        if (StrUtil.isNotBlank(qry.getPassword())) {
            String passwordDigest = Digest.md5WithSalt(qry.getPassword(), accountAggBO.getSalt());
            if (!Objects.equals(accountAggBO.getPassword(), passwordDigest)) {
                return Result.fail(ResultStatusEnum.DATA_NOT_EXIST, "user or verify error");
            }
        }
        // 4.2. 如果是验证码, 检验验证码
        else {
            return Result.fail(ResultStatusEnum.FUNCTION_IN_PROGRESS);
        }

        // 5. 账号待激活
        if (Objects.equals(AccountStatusEnum.INACTIVE.getCode(), accountAggBO.getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_STATUS_INACTIVE);
        }

        // 6. 主账号非启用状态
        if (!Objects.equals(AccountStatusEnum.ENABLE.getCode(), accountAggBO.getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_STATUS_NOT_ENABLE,
                    "账号已" + AccountStatusEnum.of(accountAggBO.getStatus()).getDesc());
        }

        // 7. 业务线账号非启用状态
        if (!Objects.equals(AccountStatusEnum.ENABLE.getCode(), accountAggBO.getAccountBizList().get(0).getStatus())) {
            return Result.fail(ResultStatusEnum.ACCOUNT_STATUS_NOT_ENABLE,
                    "账号已" + AccountStatusEnum.of(accountAggBO.getAccountBizList().get(0).getStatus()).getDesc());
        }

        // 8. 登陆成功, 返回token
        String token = accountRepository.generateToken(accountAggBO);

        return Result.success(token);
    }

    /**
     * 参数校验
     *
     * @param qry 参数
     */
    private void validate(AccountLogInQry qry) {
        Assert.isFalse(StrUtil.isAllBlank(qry.getUsername(), qry.getEmail()),
                ResultStatusEnum.PARAM_ERROR.getCode(), "username and email must not both be blank");
        Assert.isFalse(StrUtil.isAllBlank(qry.getPassword(), qry.getVerificationCode()),
                ResultStatusEnum.PARAM_ERROR.getCode(), "password and verificationCode must not both be blank");
    }

}
