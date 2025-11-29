package com.sun.foot.entity;

import java.io.Serializable;

//足球比赛的基本信息
public class FootMatch310 implements Serializable {
    private Long id;  // 主键ID
    private String competitionId;  // 比赛场次ID
    private String league;  // 赛区名称
    private String dateTime;  // 比赛时间
    private String status;  // 比赛状态
    private String homeTeam;  // 主队名称
    private String score;  // 总比分
    private String awayTeam;  // 客队名称
    private String halftimeScore;  // 半场比分
    private String square;  // 让球方

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(String competitionId) {
        this.competitionId = competitionId;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHalftimeScore() {
        return halftimeScore;
    }

    public void setHalftimeScore(String halftimeScore) {
        this.halftimeScore = halftimeScore;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }
}

