package com.sun.foot.mapper;

import com.sun.foot.entity.DistributedTaskLock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 分布式任务锁 Mapper 接口
 */
@Mapper
public interface DistributedTaskLockMapper {

    /**
     * 尝试获取分布式锁
     */
    int tryLock(@Param("taskKey") String taskKey, @Param("nodeId") String nodeId);

    /**
     * 释放分布式锁
     */
    int releaseLock(@Param("taskKey") String taskKey, @Param("nodeId") String nodeId);

    /**
     * 更新心跳时间
     */
    int updateHeartbeat(@Param("taskKey") String taskKey, @Param("nodeId") String nodeId);

    /**
     * 查询指定任务的锁状态
     */
    DistributedTaskLock getLockStatus(@Param("taskKey") String taskKey);

    /**
     * 初始化任务锁记录
     */
    int initTaskLock(@Param("taskKey") String taskKey, @Param("moduleName") String moduleName);

    /**
     * 清理过期锁
     */
    int cleanExpiredLocks(@Param("expireTime") LocalDateTime expireTime);

    /**
     * 获取所有任务状态
     */
    List<DistributedTaskLock> getAllTaskStatus();
}