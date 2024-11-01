package com.gupig.user.infra.common.until;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * 加密工具类
 *
 * @author hanbiuk
 * @date 2024-10-29
 */
public class Digest {

    /**
     * 加盐md5
     *
     * @param str  明文
     * @param salt 盐
     * @return 密文
     */
    public static String md5WithSalt(String str, String salt) {
        return DigestUtil.md5Hex(str + salt);
    }

}
