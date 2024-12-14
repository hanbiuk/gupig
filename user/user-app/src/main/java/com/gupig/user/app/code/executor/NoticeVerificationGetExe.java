package com.gupig.user.app.code.executor;

import cn.hutool.core.util.RandomUtil;
import com.gupig.user.client.common.dto.Result;
import com.gupig.user.client.common.dto.ResultStatusEnum;
import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import com.gupig.user.client.notice.enums.TouchMeansEnum;
import com.gupig.user.domain.notice.entity.NoticeVerificationBO;
import com.gupig.user.domain.notice.repo.NoticeVerificationRepository;
import com.gupig.user.infra.common.until.CdkCreator;
import com.gupig.user.infra.common.until.ThreadPoolExecutorUtil;
import com.gupig.user.infra.notice.convertor.NoticeVerificationConvertor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * 验证码获取 执行器
 *
 * @author hanbiuk
 * @date 2024-11-05
 */
@Slf4j
@Component
public class NoticeVerificationGetExe {

    @Resource
    private NoticeVerificationRepository noticeVerificationRepository;

    @Resource
    private NoticeVerificationConvertor noticeVerificationConvertor;

    /**
     * 获取验证码
     *
     * @param cmd 命令参数
     * @return 请求编码
     */
    public Result<String> execute(NoticeVerificationGetCmd cmd) {
        // 1. 参数校验
        this.validate(cmd);

        // 2. 如果触达方式不是邮箱
        if (!Objects.equals(TouchMeansEnum.EMAIL.getCode(), cmd.getTouchMeans())) {
            return Result.fail(ResultStatusEnum.FUNCTION_IN_PROGRESS);
        }

        // 3. 如果触达方式是邮箱, 发送邮件通知
        // 3.1. 生成随机验证码
        CdkCreator cdkCreator = new CdkCreator(3);
        String verificationCode = cdkCreator.getCode(String.valueOf(LocalDate.now().getDayOfMonth()), (long) cmd.getUserIdentity().charAt(0));

        // 3.2. 存入数据库
        NoticeVerificationBO noticeVerificationBO = noticeVerificationConvertor.buildAddBO(cmd, verificationCode);
        Integer addVerification = noticeVerificationRepository.add(noticeVerificationBO);
        if (addVerification <= 0) {
            log.error("NoticeVerificationGetExe execute addVerification exception, {}", addVerification);
            return Result.fail(ResultStatusEnum.SAVE_EXCEPTION);
        }

        // 3.3. 发送邮件

        // 4. 随机异步删除过期记录
        boolean deleteExpired = RandomUtil.randomInt(50) < 1;
        if (deleteExpired) {
            CompletableFuture.runAsync(() -> {
                Integer deleteExpiredCount = noticeVerificationRepository.deleteExpired(LocalDateTime.now(ZoneId.of("Asia/Shanghai")));
                log.info("NoticeVerificationGetExe execute deleteExpired deleteExpiredCount, {}", deleteExpiredCount);
            }, ThreadPoolExecutorUtil.getExecutorLow());
        }

        return Result.success(noticeVerificationBO.getRequestCode());
    }

    /**
     * 参数校验
     *
     * @param cmd 参数
     */
    private void validate(NoticeVerificationGetCmd cmd) {
        // no param to be validated
    }

}
