package com.gupig.user.app.code;

import com.gupig.user.app.code.executor.NoticeCaptchaGetExe;
import com.gupig.user.app.code.executor.NoticeVerificationGetExe;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.notice.api.NoticeService;
import com.gupig.user.client.notice.dto.NoticeCaptchaGetCmd;
import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 通知 服务实现类
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@Slf4j
@Service
@Validated
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeVerificationGetExe noticeVerificationGetExe;
    @Resource
    private NoticeCaptchaGetExe noticeCaptchaGetExe;

    /**
     * 获取验证码
     *
     * @param cmd 命令参数
     * @return 请求编码
     */
    @Override
    public Result<String> verificationGet(NoticeVerificationGetCmd cmd) {
        return noticeVerificationGetExe.execute(cmd);
    }

    /**
     * 获取图形验证码
     *
     * @param cmd 命令参数
     * @return 验证码
     */
    @Override
    public Result<String> captchaGet(NoticeCaptchaGetCmd cmd) {
        return noticeCaptchaGetExe.execute(cmd);
    }

}
