package com.football.worldcup.scoreboard;

import com.sun.istack.internal.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public class WorldCupFixtureBoard {

    private static ConcurrentHashMap<String, MatchInfo> scoreBoard = new ConcurrentHashMap<String, MatchInfo>();;
    private static int gameSequence = 0;
    private static final String MATCH = "Match";
    private static final String TXT_CREATE_ERROR = "Cannot create Match : ";
    private static final String TXT_UPDATE_ERROR = "Cannot update Match : ";

    public String createMatch(String homeTeam, String awayTeam) {
        // Input validation
        if (homeTeam == null || awayTeam == null) {
            return TXT_CREATE_ERROR+"Team names cannot be null";
        }
        else if(homeTeam==awayTeam)
            return TXT_CREATE_ERROR+"Both team names are same.";

        // Generate unique match ID without synchronization
        String matchId = generateMatchId();

        // Create and put the match details into the scoreboard
        WorldCupFixtureBoard.scoreBoard.put(matchId, new MatchInfo(homeTeam, awayTeam, 0, 0, gameSequence));

        return matchId;
    }

    // A synchronized method to generate a unique match ID
    @NotNull
    private synchronized String generateMatchId() {
        return WorldCupFixtureBoard.MATCH + ++gameSequence;
    }

    public String updateMatch(String matchID, int homeGoals, int awayGoals) {
        if(homeGoals<0 || awayGoals<0)
            return TXT_UPDATE_ERROR+"Negative Goals cannot be scored";
        else if(matchID == null)
            return TXT_UPDATE_ERROR+"MatchId cannot be NULL. This match doesn't exist";
        else
        {
            MatchInfo matchDetail = WorldCupFixtureBoard.scoreBoard.get(matchID);
            if(matchDetail == null)
                return TXT_UPDATE_ERROR+"No match details found for the associated matchId - "+matchID;
            //updating goals for the current match
            matchDetail.setHomeTeamScore(homeGoals);
            matchDetail.setAwayTeamScore(awayGoals);
            return "Hurray..!!! - Match updated Successfully for matchID - "+matchID;
        }

    }

    public String finishMatch(String matchId) {
        return "";
    }
}

