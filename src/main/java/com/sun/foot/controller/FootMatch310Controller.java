package com.sun.foot.controller;

import com.github.pagehelper.PageInfo;
import com.sun.foot.entity.FootMatch310;
import com.sun.foot.entity.FootMatch310AsianCom;
import com.sun.foot.service.FootMatch310Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/footmatch")
public class FootMatch310Controller {
    private final FootMatch310Service footMatch310Service;

    @Autowired
    public FootMatch310Controller(FootMatch310Service footMatch310Service) {
        this.footMatch310Service = footMatch310Service;
    }

    @GetMapping("/all")
    public ResponseEntity<PageInfo<FootMatch310>> getAllFootMatch310(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value = "searchLeague", required = false) String searchLeague,
            @RequestParam(value = "searchSquare", required = false) String searchSquare) {
        return ResponseEntity.ok(footMatch310Service.getAllFootMatch310(pageNum, pageSize, searchLeague, searchSquare));
    }

    @GetMapping("/queryInfo")
    public ResponseEntity<PageInfo<FootMatch310>> queryInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "15") Integer pageSize,
            @RequestParam(value = "searchLeague", required = false) String searchLeague,
            @RequestParam(value = "searchSquare", required = false) String searchSquare,
            @RequestParam(value = "initCalculationValue1", required = false) String initCalculationValue1,
            @RequestParam(value = "initCalculationValue2", required = false) String initCalculationValue2,
            @RequestParam(value = "initCalculationValue3", required = false) String initCalculationValue3,
            @RequestParam(value = "finalCalculationValue1", required = false) String finalCalculationValue1,
            @RequestParam(value = "finalCalculationValue2", required = false) String finalCalculationValue2,
            @RequestParam(value = "finalCalculationValue3", required = false) String finalCalculationValue3,
            @RequestParam(value = "upDownValue", required = false) String upDownValue,
            @RequestParam(value = "companyName", required = false) String companyName) {
        return ResponseEntity.ok(footMatch310Service.queryInfo(pageNum, pageSize, searchLeague, searchSquare,
                initCalculationValue1, initCalculationValue2, initCalculationValue3,
                finalCalculationValue1, finalCalculationValue2, finalCalculationValue3,
                upDownValue, companyName));
    }

    @GetMapping("/odds/{id}")
    public List<FootMatch310AsianCom> findByCompetitionId(@PathVariable String id,
                                                          @RequestParam(value = "initCalculationValue1", required = false) String initCalculationValue1,
                                                          @RequestParam(value = "initCalculationValue2", required = false) String initCalculationValue2,
                                                          @RequestParam(value = "initCalculationValue3", required = false) String initCalculationValue3,
                                                          @RequestParam(value = "finalCalculationValue1", required = false) String finalCalculationValue1,
                                                          @RequestParam(value = "finalCalculationValue2", required = false) String finalCalculationValue2,
                                                          @RequestParam(value = "finalCalculationValue3", required = false) String finalCalculationValue3,
                                                          @RequestParam(value = "upDownValue", required = false) String upDownValue,
                                                          @RequestParam(value = "companyName", required = false) String companyName) {
        return footMatch310Service.findByCompetitionId(id, initCalculationValue1, initCalculationValue2, initCalculationValue3, finalCalculationValue1, finalCalculationValue2, finalCalculationValue3, upDownValue, companyName);  // 根据比赛ID查询赔率
    }
}

