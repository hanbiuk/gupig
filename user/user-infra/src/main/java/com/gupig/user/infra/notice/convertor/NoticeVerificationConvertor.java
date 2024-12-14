package com.gupig.user.infra.notice.convertor;

import com.gupig.user.client.notice.dto.NoticeVerificationGetCmd;
import com.gupig.user.domain.notice.entity.NoticeVerificationBO;
import com.gupig.user.infra.common.until.SnowflakeCodeWorker;
import com.gupig.user.infra.notice.dataobject.NoticeVerificationDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * 通知验证码对象 转换器
 *
 * @author hanbiuk
 * @date 2024-12-05
 */
@Component
public class NoticeVerificationConvertor {

    /**
     * 构建 NoticeVerificationBO
     *
     * @param cmd              命令参数
     * @param verificationCode 验证码
     * @param expireMinutes    过期分钟数
     * @return NoticeVerificationBO
     */
    public NoticeVerificationBO buildAddBO(NoticeVerificationGetCmd cmd, String verificationCode, Integer... expireMinutes) {
        if (Objects.isNull(cmd)) {
            return null;
        }

        NoticeVerificationBO noticeVerificationBO = new NoticeVerificationBO();
        BeanUtils.copyProperties(cmd, noticeVerificationBO);

        String nvCode = SnowflakeCodeWorker.getInstance().nextId("NV");
        noticeVerificationBO.setCode(nvCode);

        String requestCode = SnowflakeCodeWorker.getInstance().nextId("");
        noticeVerificationBO.setRequestCode(requestCode);

        noticeVerificationBO.setVerification(verificationCode);

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));

        int expireMin = expireMinutes.length > 0 ? expireMinutes[0] : 5;
        noticeVerificationBO.setCstExpire(now.plusMinutes(expireMin));

        noticeVerificationBO.setCreator(cmd.getUserIdentity());
        noticeVerificationBO.setCstCreate(now);
        noticeVerificationBO.setModifier(cmd.getUserIdentity());
        noticeVerificationBO.setCstModify(now);

        return noticeVerificationBO;
    }

    /**
     * 转换为 NoticeVerificationDO
     *
     * @param noticeVerificationBO 业务对象
     * @return NoticeVerificationDO
     */
    public NoticeVerificationDO toDO(NoticeVerificationBO noticeVerificationBO) {
        if (Objects.isNull(noticeVerificationBO)) {
            return null;
        }

        NoticeVerificationDO noticeVerificationDO = new NoticeVerificationDO();
        BeanUtils.copyProperties(noticeVerificationBO, noticeVerificationDO);

        return noticeVerificationDO;
    }

}
