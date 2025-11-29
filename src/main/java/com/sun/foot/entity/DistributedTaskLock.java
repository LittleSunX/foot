package com.sun.foot.entity;

import java.time.LocalDateTime;

/**
 * 分布式任务锁实体类
 */
public class DistributedTaskLock {
    /**
     * 主键ID，数据库自增
     * 使用AUTO策略让数据库自动生成
     */
    private Long id;
    /**
     * 任务唯一标识
     * 用于区分不同的定时任务，例如：
     * - data_sync_task: 数据同步任务
     * - report_generation_task: 报表生成任务
     * - data_cleanup_task: 数据清理任务
     * - external_api_sync_task: 外部API同步任务
     */
    private String taskKey;
    /**
     * 所属模块名称
     * 用于按业务模块分组管理任务，便于监控和维护
     * 例如：DataSyncModule, ReportModule, DataCleanupModule
     */
    private String moduleName;
    /**
     * 当前持有锁的节点ID
     * 格式：主机名-进程ID-时间戳
     * 例如：server01-12345-1640995200000
     * 用于唯一标识集群中的某个节点实例
     */
    private String nodeId;
    /**
     * 锁状态标识
     * true: 任务正在执行中，已被某个节点锁定
     * false: 任务未执行，可以被任意节点获取锁
     * <p>
     * 这是分布式锁的核心控制字段
     */
    private Boolean isLocked;
    /**
     * 加锁时间
     * 记录任务开始执行的具体时间
     * 用途：
     * 1. 监控任务执行时长
     * 2. 超时任务的识别和处理
     * 3. 运维监控和报警
     */
    private LocalDateTime lockTime;
    /**
     * 最后心跳时间
     * 持有锁的节点定期更新此字段，证明自己还存活
     * <p>
     * 心跳机制的作用：
     * 1. 检测节点是否崩溃
     * 2. 自动释放死锁（心跳超时的锁）
     * 3. 保证集群的高可用性
     * <p>
     * 通常心跳间隔：10秒
     * 超时阈值：30秒
     */
    private LocalDateTime heartbeatTime;
    /**
     * 版本号，用于乐观锁控制
     * 每次更新记录时版本号+1
     * <p>
     * 作用：
     * 1. 防止并发更新冲突
     * 2. 保证更新操作的原子性
     * 3. 实现无锁的并发控制
     */
    private Long version;
    /**
     * 记录创建时间
     * 数据库自动设置，不需要手动赋值
     */
    private LocalDateTime createdAt;
    /**
     * 记录更新时间
     * 数据库在每次UPDATE时自动更新
     */
    private LocalDateTime updatedAt;

    // 构造函数
    public DistributedTaskLock() {}

    public DistributedTaskLock(String taskKey, String moduleName, String nodeId, Boolean isLocked) {
        this.taskKey = taskKey;
        this.moduleName = moduleName;
        this.nodeId = nodeId;
        this.isLocked = isLocked;
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public LocalDateTime getLockTime() {
        return lockTime;
    }

    public void setLockTime(LocalDateTime lockTime) {
        this.lockTime = lockTime;
    }

    public LocalDateTime getHeartbeatTime() {
        return heartbeatTime;
    }

    public void setHeartbeatTime(LocalDateTime heartbeatTime) {
        this.heartbeatTime = heartbeatTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "DistributedTaskLock{" +
                "id=" + id +
                ", taskKey='" + taskKey + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", isLocked=" + isLocked +
                ", lockTime=" + lockTime +
                ", heartbeatTime=" + heartbeatTime +
                ", version=" + version +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}