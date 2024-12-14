package com.gupig.user.infra.common.until;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 序列码生成器
 *
 * @author hanbiuk
 * @date 2024-12-04
 */
public class CdkCreator {

    /**
     * 基准字符
     */
    private static final String BASE_CHAR = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * CDK随机字符长度
     */
    private int cdkRandomLength = 8;

    private static CdkCreator cdkCreator;

    private CdkCreator() {
    }

    public static CdkCreator getCdkCreator() {
        if (cdkCreator != null) {
            return cdkCreator;
        }

        synchronized (CdkCreator.class) {
            if (cdkCreator == null) {
                cdkCreator = new CdkCreator();
            }
            return cdkCreator;
        }
    }

    /**
     * 指定参数构造
     *
     * @param cdkRandomLength CDK随机字符长度
     */
    public CdkCreator(int cdkRandomLength) {
        this.cdkRandomLength = cdkRandomLength;
    }

    /**
     * 检查CDK校验是否正常
     *
     * @param cdk 序列码
     * @return 校验是否通过
     */
    public static boolean checkCdk(String cdk) {
        int checkSum = BASE_CHAR.indexOf(cdk.charAt(0));
        int sum = 0;
        for (int i = 1; i < cdk.length(); i++) {
            char c = cdk.charAt(i);
            int index = BASE_CHAR.indexOf(c);
            sum += index;
        }
        sum = sum % BASE_CHAR.length();
        return checkSum == sum;
    }

    /**
     * 获取指定数量的序列码集合
     * 序列码格式：checkSum + batchNo + randomChar
     *
     * @param type 类型
     * @param id   唯一键
     * @param num  数量
     * @return 序列码集合
     */
    public Set<String> getCodeSet(String type, Long id, int num) {
        Set<String> set = new HashSet<>((int) (num / 0.75));
        String batchStr = type + Long.toString(id, BASE_CHAR.length()).toUpperCase();
        int sum = 0;
        for (int i = 0; i < batchStr.length(); i++) {
            char c = batchStr.charAt(i);
            int index = BASE_CHAR.indexOf(c);
            sum += index;
        }
        char[] batchNoChar = batchStr.toCharArray();

        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < num; ) {
            String cdk = this.nextCode(batchNoChar, sum, random);
            boolean success = set.add(cdk);
            if (success) {
                i++;
            }
        }

        return set;
    }

    /**
     * 获取一个序列码
     * 序列码格式：checkSum + batchNo + randomChar
     *
     * @param type 类型
     * @param id   唯一键
     * @return 序列码集合
     */
    public String getCode(String type, Long id) {
        return this.getCodeSet(type, id, 1).stream().findAny().orElse(null);
    }

    /**
     * 获取下一个序列码
     * 格式：checkSum + batchNo + randomChar
     *
     * @param batchNoChar 批次号
     * @param sum         生成数量
     * @param random      随机工具
     * @return 序列码
     */
    private String nextCode(char[] batchNoChar, int sum, Random random) {
        char[] randomChar = new char[cdkRandomLength];
        for (int i = 0; i < cdkRandomLength; i++) {
            int index = random.nextInt(BASE_CHAR.length());
            sum += index;
            randomChar[i] = BASE_CHAR.charAt(index);
        }
        char checkSumChar = BASE_CHAR.charAt(sum % BASE_CHAR.length());
        return checkSumChar + String.valueOf(batchNoChar) + String.valueOf(randomChar);
    }

}
