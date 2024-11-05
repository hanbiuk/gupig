package com.gupig.user.client.notice.api;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.notice.dto.NoticeCaptchaGetCmd;
import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import jakarta.validation.Valid;

/**
 * 通知 服务类
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
public interface NoticeService {

    /**
     * 获取验证码
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    Result<Boolean> verificationGet(@Valid NoticeVerificationGetCmd cmd);

    /**
     * 获取图形验证码
     *
     * @param cmd 命令参数
     * @return 验证码图形
     */
    Result<String> captchaGet(@Valid NoticeCaptchaGetCmd cmd);

}
