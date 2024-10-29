package com.gupig.user.domain.account.aggregate;

import com.gupig.user.domain.account.entity.AccountBO;
import com.gupig.user.domain.account.entity.AccountBizBO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户账号 聚合业务对象
 *
 * @author hanbiuk
 * @date 2024-10-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountAggBO extends AccountBO {

    /**
     * 用户账号业务线 列表
     */
    private List<AccountBizBO> accountBizList;

}
