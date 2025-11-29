package com.sun.foot.controller;

import com.sun.foot.config.SnowflakeIdGenerator;
import com.sun.foot.mapper.FootMatch310Mapper;
import com.sun.foot.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private PersonService personService;
    @Autowired
    private FootMatch310Mapper footMatch310Mapper;
    @Autowired
    private SnowflakeIdGenerator snowflakeIdGenerator;


    @GetMapping("/all")
    public String test() {
        return "雪花算法生成的id：" + snowflakeIdGenerator.nextId();
    }
}
