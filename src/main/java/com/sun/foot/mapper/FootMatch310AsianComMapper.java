package com.sun.foot.mapper;

import com.sun.foot.entity.FootMatch310AsianCom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

@Mapper
public interface FootMatch310AsianComMapper {
    @SelectProvider(type = FootMatch310AsianComSqlProvider.class, method = "findByCompetitionId")
    List<FootMatch310AsianCom> findByCompetitionId(Map<String, Object> params);  // 根据比赛ID查询赔率
}
