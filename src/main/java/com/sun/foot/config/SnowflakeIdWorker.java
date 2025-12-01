package com.sun.foot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger log = LoggerFactory.getLogger(SnowflakeIdWorker.class);

    /**
     * 时钟回拨最大容忍时间（毫秒）
     */
    private static final long MAX_BACKWARD_MS = 5L;

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
        
        log.info("Snowflake ID Worker initialized: datacenterId={}, workerId={}", datacenterId, workerId);
    }

    /**
     * 自动通过 IP Hash 计算 workerId
     */
    private long getWorkerIdByIpHash() {
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            int hash = ip.hashCode();
            long workerId = Math.abs(hash) % (maxWorkerId + 1);
            log.info("WorkerId calculated from IP: ip={}, workerId={}", ip, workerId);
            return workerId;
        } catch (UnknownHostException e) {
            // 获取 IP 失败，抛出异常，不应使用随机值
            log.error("Failed to get local IP address for workerId calculation", e);
            throw new RuntimeException("Cannot initialize SnowflakeIdWorker: failed to get local IP", e);
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
            
            // 如果回拨时间在容忍范围内，等待追上
            if (offset <= MAX_BACKWARD_MS) {
                try {
                    log.warn("Clock moved backwards by {} ms, waiting...", offset);
                    Thread.sleep(offset);
                    timestamp = currentTime();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while waiting for clock to catch up", e);
                }
            }
            
            // 二次校验，如果仍然回拨则抛出异常
            if (timestamp < lastTimestamp) {
                log.error("Clock moved backwards by {} ms, refusing to generate id", offset);
                throw new RuntimeException(
                        String.format("Clock moved backwards. Refusing to generate id for %d ms", offset));
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
            // 短暂休眠，避免 CPU 空转
            try {
                Thread.sleep(0, 100000); // 休眠 0.1 毫秒
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}
