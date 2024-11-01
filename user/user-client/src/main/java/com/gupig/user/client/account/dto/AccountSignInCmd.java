package com.gupig.user.client.account.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 账号注册并登陆 命令参数
 *
 * @author hanbiuk
 * @date 2024-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccountSignInCmd extends AccountSignUpCmd implements Serializable {

}
