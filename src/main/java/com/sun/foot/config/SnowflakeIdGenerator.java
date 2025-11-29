package com.sun.foot.config;

import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {

    private final SnowflakeIdWorker worker;

    public SnowflakeIdGenerator(SnowflakeIdWorker worker) {
        this.worker = worker;
    }


    public long nextId() {
        return worker.nextId();
    }
}
