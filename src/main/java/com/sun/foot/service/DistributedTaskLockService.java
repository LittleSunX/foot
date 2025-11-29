package com.sun.foot.service;

import com.sun.foot.entity.DistributedTaskLock;
import com.sun.foot.mapper.DistributedTaskLockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 分布式任务锁服务
 */
@Component
public class DistributedTaskLockService {
    private static final Logger log = LoggerFactory.getLogger(DistributedTaskLockService.class);
    /**
     * 注入Mapper
     */
    @Autowired
    private DistributedTaskLockMapper lockMapper;

    private final String nodeId;
    private final ScheduledExecutorService heartbeatExecutor;
    private final Map<String, ScheduledFuture<?>> heartbeatTasks = new ConcurrentHashMap<>();

    private static final int HEARTBEAT_INTERVAL_SECONDS = 10;
    private static final int LOCK_EXPIRE_SECONDS = 30;
    private static final int CLEANUP_INTERVAL_SECONDS = 30;

    public DistributedTaskLockService() {
        this.nodeId = generateNodeId();
        this.heartbeatExecutor = Executors.newScheduledThreadPool(4);
        log.info("分布式任务锁服务已初始化，节点ID: {}", nodeId);
    }

    @PostConstruct
    public void init() {
        startExpiredLockCleaner();
        log.info("分布式任务锁服务启动成功");
    }

    /**
     * 初始化任务锁记录
     */
    public void initTaskLock(String taskKey, String moduleName) {
        try {
            int result = lockMapper.initTaskLock(taskKey, moduleName);
            if (result > 0) {
                log.info("成功初始化任务锁: {} 模块: {}", taskKey, moduleName);
            } else {
                log.debug("任务锁已存在: {} 模块: {}", taskKey, moduleName);
            }
        } catch (Exception e) {
            log.debug("任务锁初始化被忽略: {} - {}", taskKey, e.getMessage());
        }
    }

    /**
     * 尝试获取任务锁
     */
    public boolean tryLock(String taskKey) {
        try {
            int affected = lockMapper.tryLock(taskKey, nodeId);

            if (affected > 0) {
                startHeartbeat(taskKey);
                log.info("成功获取任务锁: {} 节点: {}", taskKey, nodeId);
                return true;
            } else {
                log.debug("获取任务锁失败: {}，已被其他节点锁定", taskKey);
                return false;
            }
        } catch (Exception e) {
            log.error("尝试获取任务锁时发生错误: {}", taskKey, e);
            return false;
        }
    }

    /**
     * 释放任务锁
     */
    public void releaseLock(String taskKey) {
        try {
            stopHeartbeat(taskKey);

            int affected = lockMapper.releaseLock(taskKey, nodeId);

            if (affected > 0) {
                log.info("成功释放任务锁: {} 节点: {}", taskKey, nodeId);
            } else {
                log.warn("释放任务锁失败: {}，可能不是锁的持有者", taskKey);
            }
        } catch (Exception e) {
            log.error("释放任务锁时发生错误: {}", taskKey, e);
            stopHeartbeat(taskKey);
        }
    }

    /**
     * 检查当前节点是否持有指定任务的锁
     */
    public boolean isLockHolder(String taskKey) {
        try {
            DistributedTaskLock lock = lockMapper.getLockStatus(taskKey);
            return lock != null &&
                    lock.getIsLocked() &&
                    nodeId.equals(lock.getNodeId());
        } catch (Exception e) {
            log.error("检查任务锁状态时发生错误: {}", taskKey, e);
            return false;
        }
    }

    /**
     * 启动心跳任务
     */
    private void startHeartbeat(String taskKey) {
        ScheduledFuture<?> heartbeatTask = heartbeatExecutor.scheduleAtFixedRate(
                () -> {
                    try {
                        int result = lockMapper.updateHeartbeat(taskKey, nodeId);
                        if (result > 0) {
                            log.debug("更新心跳成功，任务: {} 节点: {}", taskKey, nodeId);
                        } else {
                            log.warn("更新心跳失败，任务: {}，可能已失去锁的控制权", taskKey);
                        }
                    } catch (Exception e) {
                        log.error("更新心跳失败，任务: {}", taskKey, e);
                    }
                },
                HEARTBEAT_INTERVAL_SECONDS,
                HEARTBEAT_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );

        heartbeatTasks.put(taskKey, heartbeatTask);
        log.debug("启动心跳任务: {}", taskKey);
    }

    /**
     * 停止心跳任务
     */
    private void stopHeartbeat(String taskKey) {
        ScheduledFuture<?> heartbeatTask = heartbeatTasks.remove(taskKey);
        if (heartbeatTask != null) {
            heartbeatTask.cancel(false);
            log.debug("停止心跳任务: {}", taskKey);
        }
    }

    /**
     * 启动过期锁清理任务
     */
    private void startExpiredLockCleaner() {
        heartbeatExecutor.scheduleAtFixedRate(() -> {
            try {
                LocalDateTime expireTime = LocalDateTime.now().minusSeconds(LOCK_EXPIRE_SECONDS);
                int cleanedCount = lockMapper.cleanExpiredLocks(expireTime);

                if (cleanedCount > 0) {
                    log.info("清理了 {} 个过期锁", cleanedCount);
                }
            } catch (Exception e) {
                log.error("清理过期锁时发生错误", e);
            }
        }, CLEANUP_INTERVAL_SECONDS, CLEANUP_INTERVAL_SECONDS, TimeUnit.SECONDS);
    }

    /**
     * 生成节点唯一标识
     */
    private String generateNodeId() {
        try {
            String hostname = InetAddress.getLocalHost().getHostName();
            String processInfo = ManagementFactory.getRuntimeMXBean().getName();
            long timestamp = System.currentTimeMillis();

            return hostname + "-" + processInfo + "-" + timestamp;
        } catch (Exception e) {
            log.warn("从系统信息生成节点ID失败，使用UUID作为备用方案", e);
            return "unknown-" + UUID.randomUUID();
        }
    }

    public String getNodeId() {
        return nodeId;
    }

    @PreDestroy
    public void cleanup() {
        log.info("分布式任务锁服务正在关闭...");

        heartbeatTasks.keySet().forEach(this::releaseLock);

        try {
            heartbeatExecutor.shutdown();
            if (!heartbeatExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                heartbeatExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            heartbeatExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        log.info("分布式任务锁服务关闭完成");
    }
}
