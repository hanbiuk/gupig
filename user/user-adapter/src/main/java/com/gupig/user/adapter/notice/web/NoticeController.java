package com.gupig.user.adapter.notice.web;

import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.notice.api.NoticeService;
import com.gupig.user.client.notice.dto.NoticeCaptchaGetCmd;
import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户通知 前端控制器
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@RestController
@RequestMapping("user/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 获取验证码
     *
     * @param cmd 命令参数
     * @return 请求编码
     */
    @PostMapping("verification/get")
    public Result<String> verificationGet(@RequestBody NoticeVerificationGetCmd cmd) {
        return noticeService.verificationGet(cmd);
    }

    /**
     * 获取图形验证码
     *
     * @param cmd 命令参数
     * @return 验证码
     */
    @PostMapping("captcha/get")
    public Result<String> captchaGet(@RequestBody NoticeCaptchaGetCmd cmd) {
        return noticeService.captchaGet(cmd);
    }

}
