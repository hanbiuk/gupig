package com.gupig.user.client.account.dto;

import com.gupig.user.client.common.dto.ContextDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账号登出 命令参数
 *
 * @author hanbiuk
 * @date 2024-10-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountLogOutCmd extends ContextDTO implements Serializable {

}
