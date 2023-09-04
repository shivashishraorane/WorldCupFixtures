package com.football.worldcup.scoreboard;

public class MatchInfo {
    private String homeTeam;
    private String awayTeam;
    private Integer homeTeamGoals;
    private Integer awayTeamGoals;
    private Integer gameSequence;

    public MatchInfo(String homeTeam, String awayTeam, Integer homeTeamGoals, Integer awayTeamGoals,
                        Integer gameSequence) {
        super();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeTeamGoals = homeTeamGoals;
        this.awayTeamGoals = awayTeamGoals;
        this.gameSequence = gameSequence;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getHomeTeamGoals() {
        return homeTeamGoals;
    }

    public void setHomeTeamGoals(Integer homeTeamGoals) {
        this.homeTeamGoals = homeTeamGoals;
    }

    public Integer getAwayTeamGoals() {
        return awayTeamGoals;
    }

    public void setAwayTeamGoals(Integer awayTeamGoals) {
        this.awayTeamGoals = awayTeamGoals;
    }

    public Integer getGameSequence() {
        return gameSequence;
    }

    public void setGameSequence(Integer gameSequence) {
        this.gameSequence = gameSequence;
    }

}