package com.gupig.user.domain.notice.repo;

import com.gupig.user.domain.notice.entity.NoticeVerificationBO;

import java.time.LocalDateTime;

/**
 * 通知验证码 资源库类
 *
 * @author hanbiuk
 * @date 2024-10-25
 */
public interface NoticeVerificationRepository {

    /**
     * 新增记录
     *
     * @param noticeVerificationBO 验证码信息
     * @return 受影响行数
     */
    Integer add(NoticeVerificationBO noticeVerificationBO);

    /**
     * 删除过期记录
     *
     * @param cstExpire 过期时间
     * @return 受影响行数
     */
    Integer deleteExpired(LocalDateTime cstExpire);

}
