package com.gupig.user.app.code.executor;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import org.springframework.stereotype.Component;

/**
 * 验证码获取 执行器
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@Component
public class NoticeVerificationGetExe {

    /**
     * 获取验证码
     *
     * @param cmd 命令参数
     * @return 是否成功
     */
    public Result<Boolean> execute(NoticeVerificationGetCmd cmd) {
        return null;
    }

}
