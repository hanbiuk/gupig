package com.gupig.user.infra.common.until;

import cn.hutool.core.util.RandomUtil;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * 雪花算法编码生成器
 *
 * @author hanbiuk
 * @date 2024-10-30
 */
public class SnowflakeCodeWorker {

    /**
     * 开始时间截 (2024-11-01)
     */
    private static final long EPOCH = 1730390401000L;
    /**
     * 机器id所占的位数
     */
    private static final long WORKER_ID_BITS = 5L;
    /**
     * 数据标识id所占的位数
     */
    private static final long DATACENTER_ID_BITS = 5L;
    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /**
     * 支持的最大数据标识id，结果是31
     */
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    /**
     * 序列在id中占的位数
     */
    private static final long SEQUENCE_BITS = 12L;
    /**
     * 机器ID向左移12位
     */
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /**
     * 数据标识id向左移17位(12+5)
     */
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /**
     * 时间截向左移22位(5+5+12)
     */
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /**
     * 工作机器ID(0~31)
     */
    private final long workerId;
    /**
     * 数据中心ID(0~31)
     */
    private final long datacenterId;
    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    private static final SnowflakeCodeWorker INSTANCE = new SnowflakeCodeWorker(getWorkerId(), getDataCenterId());

    /**
     * 默认按hostName/ip生成WorkerId/DataCenterId的单例构造
     * 有小概率重复
     *
     * @return 实例化对象
     */
    public static SnowflakeCodeWorker getInstance() {
        return INSTANCE;
    }

    /**
     * 按传入的WorkerId/DataCenterId构造-没有特殊需求用getInstance单例构造
     * 不同节点进行不同配置
     *
     * @param workerId     工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     */
    public SnowflakeCodeWorker(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("workerId can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenterId can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获得下一个ID
     *
     * @return SnowflakeId
     */
    public synchronized Long nextId() {
        long timestamp = System.currentTimeMillis();
        // 如果当前时间小于上一次ID生成的时间戳, 说明系统时钟回退过, 随机生成
        if (timestamp < lastTimestamp) {
            return RandomUtil.randomLong(Long.MAX_VALUE - 10000000L, Long.MAX_VALUE);
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = RandomUtil.randomLong(0, 7);
        }
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    /**
     * 获得下一个ID
     *
     * @return SnowflakeId
     */
    public synchronized String nextId(String prefix) {
        return prefix + nextId();
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private Long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 通过ip生成工作ID
     *
     * @return 工作ID
     */
    private static Long getWorkerId() {
        try {
            String hostAddress = Inet4Address.getLocalHost().getHostAddress();
            int[] codePoints = hostAddress.codePoints().toArray();
            int sums = 0;
            for (int b : codePoints) {
                sums += b;
            }
            return sums & MAX_WORKER_ID;
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtil.randomLong(0, MAX_WORKER_ID);
        }
    }

    /**
     * 通过hostName生成数据中心ID
     *
     * @return 数据中心ID
     */
    private static Long getDataCenterId() {
        try {
            String hostName = Inet4Address.getLocalHost().getHostName();
            int[] codePoints = hostName.codePoints().toArray();
            int sums = 0;
            for (int i : codePoints) {
                sums += i;
            }
            return sums & MAX_DATACENTER_ID;
        } catch (UnknownHostException e) {
            // 如果获取失败，则使用随机数备用
            return RandomUtil.randomLong(0, MAX_WORKER_ID);
        }
    }

}
