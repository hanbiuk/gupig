package com.gupig.user.infra.notice.mapper;

import com.gupig.user.infra.notice.dataobject.NoticeVerificationDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * 通知验证码 数据操作
 *
 * @author hanbiuk
 * @date 2024-12-14
 */
@Mapper
public interface NoticeVerificationMapper {

    /**
     * 根据主键编码删除
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer deleteByCode(NoticeVerificationDO record);

    /**
     * 新增记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insert(NoticeVerificationDO record);

    /**
     * 新增非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insertSelective(NoticeVerificationDO record);

    /**
     * 根据主键编码查询
     *
     * @param code 主键编码
     * @return 记录
     */
    NoticeVerificationDO selectByCode(String code);

    /**
     * 根据主键编码更新非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCodeSelective(NoticeVerificationDO record);

    /**
     * 根据主键编码更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCode(NoticeVerificationDO record);

    /**
     * 删除过期记录
     *
     * @param cstExpire 过期时间
     * @return 受影响行数
     */
    Integer deleteExpired(@Param("cstExpire") LocalDateTime cstExpire);

}
