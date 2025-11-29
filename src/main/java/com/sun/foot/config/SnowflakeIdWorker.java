package com.sun.foot.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 企业级 Snowflake ID 生成器
 * <p>
 * 功能：
 * 1. 数据中心 ID 由配置文件指定
 * 2. workerId 自动从本机 IP Hash 计算（0~31），无运维成本
 * 3. 支持时钟回拨处理（阻塞等待 lastTimestamp）
 * 4. 单节点毫秒内 4096 序列自增
 */
public class SnowflakeIdWorker {

    /**
     * 开始时间戳（可以固定为项目开始时间）
     */
    private final long twepoch = 1577808000000L; // 2020-01-01 00:00:00

    /**
     * 序列号占用的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器 ID 占用的位数
     */
    private final long workerIdBits = 5L;

    /**
     * 数据中心 ID 占用的位数
     */
    private final long datacenterIdBits = 5L;

    /**
     * 最大机器 ID：31
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /**
     * 最大数据中心 ID：31
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /**
     * 每毫秒最大序列号：4095
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param datacenterId 数据中心 ID（机房）
     */
    public SnowflakeIdWorker(long datacenterId) {
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException("datacenterId must be between 0 and 31");
        }

        this.datacenterId = datacenterId;
        this.workerId = getWorkerIdByIpHash();
    }

    /**
     * 自动通过 IP Hash 计算 workerId
     */
    private long getWorkerIdByIpHash() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            int hash = ip.hashCode();
            return Math.abs(hash) % (maxWorkerId + 1);
        } catch (UnknownHostException e) {
            // 如果获取 IP 失败，使用随机值（低概率）
            return (long) (Math.random() * (maxWorkerId + 1));
        }
    }

    /**
     * 获取下一个 ID（线程安全）
     */
    public synchronized long nextId() {
        long timestamp = currentTime();

        // 检测时钟回拨
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;

            try {
                // 企业级稳定方案：阻塞等待 offset 毫秒
                Thread.sleep(offset);
                timestamp = currentTime();
                // 二次校验
                if (timestamp < lastTimestamp) {
                    throw new RuntimeException(
                            "Clock moved backwards. Refusing to generate id for " + offset + " ms");
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (timestamp == lastTimestamp) {
            // 同一毫秒内，序列递增
            sequence = (sequence + 1) & sequenceMask;
            // 同一毫秒序列号溢出，等待下一毫秒
            if (sequence == 0) {
                timestamp = nextMillis(lastTimestamp);
            }
        } else {
            // 新毫秒序列号归零
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        // 拼接 ID
        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }

    private long nextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}
