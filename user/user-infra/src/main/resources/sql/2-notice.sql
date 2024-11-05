-- 1. 用户通知验证码表
CREATE TABLE `user_notice_verification`
(
    `id`            bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键编号',
    `code`          varchar(32)         NOT NULL DEFAULT '' COMMENT '主键编码',
    `tenant_code`   varchar(32)         NOT NULL DEFAULT '' COMMENT '租户编码',
    `biz_code`      varchar(32)         NOT NULL DEFAULT '' COMMENT '业务线',
    `function_code` varchar(32)         NOT NULL DEFAULT '' COMMENT '场景',
    `touch_means`   varchar(32)         NOT NULL DEFAULT '' COMMENT '触达方式',
    `user_identity` varchar(32)         NOT NULL DEFAULT '' COMMENT '用户身份',
    `request_code`  varchar(32)         NOT NULL DEFAULT '' COMMENT '请求编码',
    `verification`  varchar(32)         NOT NULL DEFAULT '' COMMENT '验证码',
    `cst_expire`    datetime            NOT NULL DEFAULT '9999-12-31 23:59:59' COMMENT '过期时间',
    `is_deleted`    tinyint(4)          NOT NULL DEFAULT '0' COMMENT '是否删除: 0-否, 1-是',
    `creator`       varchar(32)         NOT NULL DEFAULT '' COMMENT '创建人',
    `cst_create`    datetime            NOT NULL DEFAULT '2000-01-01 00:00:01' COMMENT '创建时间',
    `modifier`      varchar(32)         NOT NULL DEFAULT '' COMMENT '修改人',
    `cst_modify`    datetime            NOT NULL DEFAULT '2000-01-01 00:00:01' COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_code` (`code`) USING BTREE,
    KEY `idx_tenant_biz_func_user` (`tenant_code`, `biz_code`, `function_code`, `user_identity`) USING BTREE,
    KEY `idx_expire` (`cst_expire`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT ='用户通知验证码表';