package com.sun.foot.service;

import com.sun.foot.entity.DistributedTaskLock;
import com.sun.foot.mapper.DistributedTaskLockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class AsyncTaskService {
    private static final Logger log = LoggerFactory.getLogger(AsyncTaskService.class);

    @Autowired
    private DistributedTaskLockService lockService;

    @Autowired
    private DistributedTaskLockMapper lockMapper;

    private static final String TASK_KEY = "data_sync_task";
    private static final String MODULE_NAME = "DataSyncModule";

    @PostConstruct
    public void init() {
        lockService.initTaskLock(TASK_KEY, MODULE_NAME);
    }

    @Async("asyncTaskPool")
    public void executeAsyncTask() {
        log.info("测试异步是否成功，executeAsyncTask thread = {}", Thread.currentThread().getName());
        if (lockService.tryLock(TASK_KEY)) {
            try {
                log.info("开始执行数据同步任务，节点：{}", lockService.getNodeId());
                List<DistributedTaskLock> allTaskStatus = lockMapper.getAllTaskStatus();
                allTaskStatus.forEach(System.out::println);
                log.info("数据同步任务执行完成");
            } catch (Exception e) {
                log.error("数据同步任务执行失败", e);
            } finally {
                lockService.releaseLock(TASK_KEY);
            }
        } else {
            log.debug("其他节点正在执行数据同步任务，本次跳过");
        }
    }
}
