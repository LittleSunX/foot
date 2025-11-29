package com.sun.foot.controller;

import com.sun.foot.entity.DistributedTaskLock;
import com.sun.foot.mapper.DistributedTaskLockMapper;
import com.sun.foot.service.DistributedTaskLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/tasks")
public class TaskMonitorController {

    @Autowired
    private DistributedTaskLockMapper lockMapper;

    @Autowired
    private DistributedTaskLockService lockService;

    @GetMapping("/status")
    public ResponseEntity<List<TaskStatusDto>> getAllTaskStatus() {
        List<DistributedTaskLock> locks = lockMapper.getAllTaskStatus();

        List<TaskStatusDto> statusList = locks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusList);
    }

    @PostMapping("/release/{taskKey}")
    public ResponseEntity<Map<String, Object>> forceReleaseLock(@PathVariable String taskKey) {
        try {
            lockService.releaseLock(taskKey);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "任务锁已被释放: " + taskKey);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "[" + taskKey + "]" + "未能释放锁: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private TaskStatusDto convertToDto(DistributedTaskLock lock) {
        TaskStatusDto dto = new TaskStatusDto();
        dto.setTaskKey(lock.getTaskKey());
        dto.setModuleName(lock.getModuleName());
        dto.setLocked(lock.getIsLocked());
        dto.setNodeId(lock.getNodeId());
        dto.setLockTime(lock.getLockTime());
        dto.setHeartbeatTime(lock.getHeartbeatTime());
        return dto;
    }
}

class TaskStatusDto {
    private String taskKey;
    private String moduleName;
    private Boolean locked;
    private String nodeId;
    private LocalDateTime lockTime;
    private LocalDateTime heartbeatTime;

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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
}
