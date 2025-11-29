package com.sun.foot.mapper;

import com.sun.foot.entity.FootMatch310;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface FootMatch310Mapper {
    @SelectProvider(type = FootMatch310SqlProvider.class, method = "findAll")
    List<FootMatch310> findAll(Map<String, Object> params);  // 查询所有比赛

    @SelectProvider(type = FootMatch310SqlProvider.class, method = "queryInfo")
    List<FootMatch310> queryInfo(Map<String, Object> params);  //根据查询条件查询相对的内容
}

