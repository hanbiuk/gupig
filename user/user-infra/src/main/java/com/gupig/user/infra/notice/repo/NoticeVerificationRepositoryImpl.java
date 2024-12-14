package com.gupig.user.infra.notice.repo;

import com.gupig.user.domain.notice.entity.NoticeVerificationBO;
import com.gupig.user.domain.notice.repo.NoticeVerificationRepository;
import com.gupig.user.infra.notice.convertor.NoticeVerificationConvertor;
import com.gupig.user.infra.notice.dataobject.NoticeVerificationDO;
import com.gupig.user.infra.notice.mapper.NoticeVerificationMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通知验证码 资源库实现类
 *
 * @author hanbiuk
 * @date 2024-12-14
 */
@Repository
public class NoticeVerificationRepositoryImpl implements NoticeVerificationRepository {

    @Resource
    private NoticeVerificationMapper noticeVerificationMapper;

    @Resource
    private NoticeVerificationConvertor noticeVerificationConvertor;

    /**
     * 新增记录
     *
     * @param noticeVerificationBO 验证码信息
     * @return 受影响行数
     */
    @Override
    public Integer add(NoticeVerificationBO noticeVerificationBO) {
        if (Objects.isNull(noticeVerificationBO)) {
            return 0;
        }

        NoticeVerificationDO noticeVerificationDO = noticeVerificationConvertor.toDO(noticeVerificationBO);
        return noticeVerificationMapper.insertSelective(noticeVerificationDO);
    }

    /**
     * 删除过期记录
     *
     * @param cstExpire 过期时间
     * @return 受影响行数
     */
    @Override
    public Integer deleteExpired(LocalDateTime cstExpire) {
        return noticeVerificationMapper.deleteExpired(cstExpire);
    }

}
