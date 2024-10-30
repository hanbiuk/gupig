package com.gupig.user.infra.account.mapper;

import com.gupig.user.infra.account.dataobject.AccountLogoutDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账号登出表 数据操作
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
@Mapper
public interface AccountLogoutMapper {

    /**
     * 根据主键编码删除
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer deleteByCode(AccountLogoutDO record);

    /**
     * 新增记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insert(AccountLogoutDO record);

    /**
     * 新增非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insertSelective(AccountLogoutDO record);

    /**
     * 根据主键编码查询
     *
     * @param code 主键编码
     * @return 记录
     */
    AccountLogoutDO selectByCode(String code);

    /**
     * 根据主键编码更新非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCodeSelective(AccountLogoutDO record);

    /**
     * 根据主键编码更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCode(AccountLogoutDO record);

}