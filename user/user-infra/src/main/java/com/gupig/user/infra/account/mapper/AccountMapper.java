package com.gupig.user.infra.account.mapper;

import com.gupig.user.client.account.dto.AccountLogInCmd;
import com.gupig.user.infra.account.dataobject.AccountDO;
import com.gupig.user.infra.account.dataobject.AccountWithBizDataResult;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户账号表 数据操作
 *
 * @author hanbiuk
 * @date 2024-10-28
 */
@Mapper
public interface AccountMapper {

    /**
     * 根据主键编码删除
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer deleteByCode(AccountDO record);

    /**
     * 新增记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insert(AccountDO record);

    /**
     * 新增非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer insertSelective(AccountDO record);

    /**
     * 根据主键编码查询
     *
     * @param code 主键编码
     * @return 记录
     */
    AccountDO selectByCode(String code);

    /**
     * 根据主键编码更新非空字段记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCodeSelective(AccountDO record);

    /**
     * 根据主键编码更新记录
     *
     * @param record 记录
     * @return 受影响行数
     */
    Integer updateByCode(AccountDO record);

    /**
     * 根据业务线获取账号信息
     *
     * @param cmd 命令参数
     * @return 账号及业务线结果
     */
    AccountWithBizDataResult selectByBiz(AccountLogInCmd cmd);

}
