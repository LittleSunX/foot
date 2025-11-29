package com.sun.foot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SchedulingService {

    @Autowired
    private AsyncTaskService asyncTaskService;

    @Scheduled(cron = "0 */1 * * * *")
    public void scheduled() {
        asyncTaskService.executeAsyncTask(); // ✅ 通过 Spring 代理调用，@Async 才能生效
    }

}
