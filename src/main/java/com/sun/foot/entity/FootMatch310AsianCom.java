package com.sun.foot.entity;

import java.io.Serializable;
import java.math.BigDecimal;


//足球比赛的亚洲赔率信息
public class FootMatch310AsianCom implements Serializable {
    private Long id;  // 主键ID
    private String companyId;  // 公司ID
    private String companyName;  // 公司名称
    private BigDecimal initHomeWin;  // 初始主胜
    private BigDecimal initDraw;  // 初始和
    private BigDecimal initAwayWin;  // 初始客胜
    private String initHomeWinRate;  // 初始主胜率
    private String initDrawRate;  // 初始和率
    private String initAwayWinRate;  // 初始客胜率
    private String initReturnRate;  // 初始返还率
    private BigDecimal initKelly1;  // 初始凯利1
    private BigDecimal initKelly2;  // 初始凯利2
    private BigDecimal initKelly3;  // 初始凯利3
    private BigDecimal finalHomeWin;  // 最终主胜
    private BigDecimal finalDraw;  // 最终和
    private BigDecimal finalAwayWin;  // 最终客胜
    private String finalHomeWinRate;  // 最终主胜率
    private String finalDrawRate;  // 最终和率
    private String finalAwayWinRate;  // 最终客胜率
    private String finalReturnRate;  // 最终返还率
    private BigDecimal finalKelly1;  // 最终凯利1
    private BigDecimal finalKelly2;  // 最终凯利2
    private BigDecimal finalKelly3;  // 最终凯利3
    private BigDecimal initCalculationValue1;  // 初始计算值1
    private BigDecimal initCalculationValue2;  // 初始计算值2
    private BigDecimal initCalculationValue3;  // 初始计算值3
    private BigDecimal finalCalculationValue1;  // 最终计算值1
    private BigDecimal finalCalculationValue2;  // 最终计算值2
    private BigDecimal finalCalculationValue3;  // 最终计算值3
    private String upDownValue;  // 升降值
    private String initAsianHandicap;  // 最初让球指数
    private BigDecimal initOverUnder;  // 最初进球指数
    private String finalAsianHandicap;  // 最终让球指数
    private BigDecimal finalOverUnder;  // 最终进球指数
    private String competitionId;  // 主表id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public BigDecimal getInitHomeWin() {
        return initHomeWin;
    }

    public void setInitHomeWin(BigDecimal initHomeWin) {
        this.initHomeWin = initHomeWin;
    }

    public BigDecimal getInitDraw() {
        return initDraw;
    }

    public void setInitDraw(BigDecimal initDraw) {
        this.initDraw = initDraw;
    }

    public BigDecimal getInitAwayWin() {
        return initAwayWin;
    }

    public void setInitAwayWin(BigDecimal initAwayWin) {
        this.initAwayWin = initAwayWin;
    }

    public String getInitHomeWinRate() {
        return initHomeWinRate;
    }

    public void setInitHomeWinRate(String initHomeWinRate) {
        this.initHomeWinRate = initHomeWinRate;
    }

    public String getInitDrawRate() {
        return initDrawRate;
    }

    public void setInitDrawRate(String initDrawRate) {
        this.initDrawRate = initDrawRate;
    }

    public String getInitAwayWinRate() {
        return initAwayWinRate;
    }

    public void setInitAwayWinRate(String initAwayWinRate) {
        this.initAwayWinRate = initAwayWinRate;
    }

    public String getInitReturnRate() {
        return initReturnRate;
    }

    public void setInitReturnRate(String initReturnRate) {
        this.initReturnRate = initReturnRate;
    }

    public BigDecimal getInitKelly1() {
        return initKelly1;
    }

    public void setInitKelly1(BigDecimal initKelly1) {
        this.initKelly1 = initKelly1;
    }

    public BigDecimal getInitKelly2() {
        return initKelly2;
    }

    public void setInitKelly2(BigDecimal initKelly2) {
        this.initKelly2 = initKelly2;
    }

    public BigDecimal getInitKelly3() {
        return initKelly3;
    }

    public void setInitKelly3(BigDecimal initKelly3) {
        this.initKelly3 = initKelly3;
    }

    public BigDecimal getFinalHomeWin() {
        return finalHomeWin;
    }

    public void setFinalHomeWin(BigDecimal finalHomeWin) {
        this.finalHomeWin = finalHomeWin;
    }

    public BigDecimal getFinalDraw() {
        return finalDraw;
    }

    public void setFinalDraw(BigDecimal finalDraw) {
        this.finalDraw = finalDraw;
    }

    public BigDecimal getFinalAwayWin() {
        return finalAwayWin;
    }

    public void setFinalAwayWin(BigDecimal finalAwayWin) {
        this.finalAwayWin = finalAwayWin;
    }

    public String getFinalHomeWinRate() {
        return finalHomeWinRate;
    }

    public void setFinalHomeWinRate(String finalHomeWinRate) {
        this.finalHomeWinRate = finalHomeWinRate;
    }

    public String getFinalDrawRate() {
        return finalDrawRate;
    }

    public void setFinalDrawRate(String finalDrawRate) {
        this.finalDrawRate = finalDrawRate;
    }

    public String getFinalAwayWinRate() {
        return finalAwayWinRate;
    }

    public void setFinalAwayWinRate(String finalAwayWinRate) {
        this.finalAwayWinRate = finalAwayWinRate;
    }

    public String getFinalReturnRate() {
        return finalReturnRate;
    }

    public void setFinalReturnRate(String finalReturnRate) {
        this.finalReturnRate = finalReturnRate;
    }

    public BigDecimal getFinalKelly1() {
        return finalKelly1;
    }

    public void setFinalKelly1(BigDecimal finalKelly1) {
        this.finalKelly1 = finalKelly1;
    }

    public BigDecimal getFinalKelly2() {
        return finalKelly2;
    }

    public void setFinalKelly2(BigDecimal finalKelly2) {
        this.finalKelly2 = finalKelly2;
    }

    public BigDecimal getFinalKelly3() {
        return finalKelly3;
    }

    public void setFinalKelly3(BigDecimal finalKelly3) {
        this.finalKelly3 = finalKelly3;
    }

    public BigDecimal getInitCalculationValue1() {
        return initCalculationValue1;
    }

    public void setInitCalculationValue1(BigDecimal initCalculationValue1) {
        this.initCalculationValue1 = initCalculationValue1;
    }

    public BigDecimal getInitCalculationValue2() {
        return initCalculationValue2;
    }

    public void setInitCalculationValue2(BigDecimal initCalculationValue2) {
        this.initCalculationValue2 = initCalculationValue2;
    }

    public BigDecimal getInitCalculationValue3() {
        return initCalculationValue3;
    }

    public void setInitCalculationValue3(BigDecimal initCalculationValue3) {
        this.initCalculationValue3 = initCalculationValue3;
    }

    public BigDecimal getFinalCalculationValue1() {
        return finalCalculationValue1;
    }

    public void setFinalCalculationValue1(BigDecimal finalCalculationValue1) {
        this.finalCalculationValue1 = finalCalculationValue1;
    }

    public BigDecimal getFinalCalculationValue2() {
        return finalCalculationValue2;
    }

    public void setFinalCalculationValue2(BigDecimal finalCalculationValue2) {
        this.finalCalculationValue2 = finalCalculationValue2;
    }

    public BigDecimal getFinalCalculationValue3() {
        return finalCalculationValue3;
    }

    public void setFinalCalculationValue3(BigDecimal finalCalculationValue3) {
        this.finalCalculationValue3 = finalCalculationValue3;
    }

    public String getUpDownValue() {
        return upDownValue;
    }

    public void setUpDownValue(String upDownValue) {
        this.upDownValue = upDownValue;
    }

    public String getInitAsianHandicap() {
        return initAsianHandicap;
    }

    public void setInitAsianHandicap(String initAsianHandicap) {
        this.initAsianHandicap = initAsianHandicap;
    }

    public BigDecimal getInitOverUnder() {
        return initOverUnder;
    }

    public void setInitOverUnder(BigDecimal initOverUnder) {
        this.initOverUnder = initOverUnder;
    }

    public String getFinalAsianHandicap() {
        return finalAsianHandicap;
    }

    public void setFinalAsianHandicap(String finalAsianHandicap) {
        this.finalAsianHandicap = finalAsianHandicap;
    }

    public BigDecimal getFinalOverUnder() {
        return finalOverUnder;
    }

    public void setFinalOverUnder(BigDecimal finalOverUnder) {
        this.finalOverUnder = finalOverUnder;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }
}

