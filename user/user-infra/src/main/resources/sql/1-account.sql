-- 1. 用户账号表
CREATE TABLE `user_account`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `code`         varchar(32)  NOT NULL DEFAULT '' COMMENT '主键编码',
    `tenant_code`  varchar(32)  NOT NULL DEFAULT '' COMMENT '租户编码',
    `username`     varchar(32)  NOT NULL DEFAULT '' COMMENT '用户名',
    `real_name`    varchar(32)  NOT NULL DEFAULT '' COMMENT '真实姓名',
    `nickname`     varchar(32)  NOT NULL DEFAULT '' COMMENT '昵称',
    `password`     varchar(128) NOT NULL DEFAULT '' COMMENT '密码',
    `salt`         varchar(32)  NOT NULL DEFAULT '' COMMENT '密码加盐',
    `email`        varchar(64)  NOT NULL DEFAULT '' COMMENT '邮箱',
    `mobile`       varchar(64)  NOT NULL DEFAULT '' COMMENT '手机号',
    `gender`       tinyint(4)          NOT NULL DEFAULT '0' COMMENT '性别: 0-未知, 1-男, 2-女',
    `birthday`     date         NOT NULL DEFAULT '2000-01-01' COMMENT '出生年月日',
    `portrait`     varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `status`       tinyint(4)          NOT NULL DEFAULT '0' COMMENT '状态: 0-待激活, 1-启用, 2-禁用, 3-销号',
    `cst_activate` datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '激活时间',
    `is_deleted`   tinyint(4)          NOT NULL DEFAULT '0' COMMENT '是否删除: 0-否, 1-是',
    `creator`      varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
    `cst_create`   datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
    `modifier`     varchar(32)  NOT NULL DEFAULT '' COMMENT '修改人',
    `cst_modify`   datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE,
    UNIQUE KEY `uk_tenant_email` (`tenant_code`, `email`) USING BTREE,
    UNIQUE KEY `uk_tenant_name` (`tenant_code`, `username`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户账号表';

-- 2. 用户账号业务线表
CREATE TABLE `user_account_biz`
(
    `id`           bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `code`         varchar(32)  NOT NULL DEFAULT '' COMMENT '主键编码',
    `tenant_code`  varchar(32)  NOT NULL DEFAULT '' COMMENT '租户编码',
    `ua_code`      varchar(32)  NOT NULL DEFAULT '' COMMENT '用户账号编码',
    `biz_code`     varchar(32)  NOT NULL DEFAULT '' COMMENT '业务线',
    `nickname`     varchar(32)  NOT NULL DEFAULT '' COMMENT '昵称',
    `portrait`     varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
    `user_detail`  varchar(256) NOT NULL DEFAULT '' COMMENT '自我简介/签名',
    `status`       tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态: 1-启用, 2-禁用, 3-销号',
    `cst_activate` datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '激活时间',
    `is_deleted`   tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除: 0-否, 1-是',
    `creator`      varchar(32)  NOT NULL DEFAULT '' COMMENT '创建人',
    `cst_create`   datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '创建时间',
    `modifier`     varchar(32)  NOT NULL DEFAULT '' COMMENT '修改人',
    `cst_modify`   datetime     NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE,
    UNIQUE KEY `uk_tenant_ua_biz` (`tenant_code`,`ua_code`,`biz_code`) USING BTREE
) ENGINE=InnoDB
    DEFAULT CHARSET=utf8mb4
    COLLATE=utf8mb4_unicode_ci COMMENT='用户账号业务线表';
