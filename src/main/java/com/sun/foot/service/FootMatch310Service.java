package com.sun.foot.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.foot.entity.FootMatch310;
import com.sun.foot.entity.FootMatch310AsianCom;
import com.sun.foot.mapper.FootMatch310AsianComMapper;
import com.sun.foot.mapper.FootMatch310Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FootMatch310Service {
    private final FootMatch310Mapper footMatch310Mapper;
    private final FootMatch310AsianComMapper footMatch310AsianComMapper;

    @Autowired
    public FootMatch310Service(FootMatch310Mapper footMatch310Mapper, FootMatch310AsianComMapper footMatch310AsianComMapper) {
        this.footMatch310Mapper = footMatch310Mapper;
        this.footMatch310AsianComMapper = footMatch310AsianComMapper;
    }

    public PageInfo<FootMatch310> getAllFootMatch310(Integer pageNum, Integer pageSize, String searchLeague, String searchSquare) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("searchLeague", searchLeague);
        map.put("searchSquare", searchSquare);
        List<FootMatch310> footMatch310List = footMatch310Mapper.findAll(map);
        return new PageInfo<>(footMatch310List);
    }

    public PageInfo<FootMatch310> queryInfo(Integer pageNum, Integer pageSize, String searchLeague, String searchSquare,
                                            String initCalculationValue1,
                                            String initCalculationValue2,
                                            String initCalculationValue3,
                                            String finalCalculationValue1,
                                            String finalCalculationValue2,
                                            String finalCalculationValue3,
                                            String upDownValue,
                                            String companyName) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("searchLeague", searchLeague);
        map.put("searchSquare", searchSquare);
        extracted(initCalculationValue1, initCalculationValue2, initCalculationValue3, finalCalculationValue1, finalCalculationValue2, finalCalculationValue3, upDownValue, companyName, map);
        List<FootMatch310> footMatch310List = footMatch310Mapper.queryInfo(map);
        return new PageInfo<>(footMatch310List);
    }

    /**
     * 公共参数
     */
    private static void extracted(String initCalculationValue1, String initCalculationValue2, String initCalculationValue3, String finalCalculationValue1, String finalCalculationValue2, String finalCalculationValue3, String upDownValue, String companyName, Map<String, Object> map) {
        map.put("initCalculationValue1", initCalculationValue1);
        map.put("initCalculationValue2", initCalculationValue2);
        map.put("initCalculationValue3", initCalculationValue3);
        map.put("finalCalculationValue1", finalCalculationValue1);
        map.put("finalCalculationValue2", finalCalculationValue2);
        map.put("finalCalculationValue3", finalCalculationValue3);
        map.put("upDownValue", upDownValue);
        map.put("companyName", companyName);
    }

    public List<FootMatch310AsianCom> findByCompetitionId(String competitionId,
                                                          String initCalculationValue1,
                                                          String initCalculationValue2,
                                                          String initCalculationValue3,
                                                          String finalCalculationValue1,
                                                          String finalCalculationValue2,
                                                          String finalCalculationValue3,
                                                          String upDownValue,
                                                          String companyName) {
        Map<String, Object> map = new HashMap<>();
        map.put("competitionId", competitionId);
        extracted(initCalculationValue1, initCalculationValue2, initCalculationValue3, finalCalculationValue1, finalCalculationValue2, finalCalculationValue3, upDownValue, companyName, map);
        return footMatch310AsianComMapper.findByCompetitionId(map);  // 根据比赛ID查询赔率
    }

    public String test() {
        return "hello world";
    }
}
