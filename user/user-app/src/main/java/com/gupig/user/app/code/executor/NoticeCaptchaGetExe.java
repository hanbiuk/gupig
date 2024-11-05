package com.gupig.user.app.code.executor;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.notice.dto.NoticeCaptchaGetCmd;
import org.springframework.stereotype.Component;

/**
 * 图形验证码获取 执行器
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@Component
public class NoticeCaptchaGetExe {

    /**
     * 获取图形验证码
     *
     * @param cmd 命令参数
     * @return 验证码
     */
    public Result<String> execute(NoticeCaptchaGetCmd cmd) {
        return null;
    }

}
