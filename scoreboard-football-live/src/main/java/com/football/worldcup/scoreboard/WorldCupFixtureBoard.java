package com.football.worldcup.scoreboard;

import java.util.concurrent.ConcurrentHashMap;

public class WorldCupFixtureBoard {

    private static ConcurrentHashMap<String, MatchInfo> scoreBoard = new ConcurrentHashMap<String, MatchInfo>();;
    private static int gameSequence = 0;
    private static final String MATCH = "Match";
    private static final String TXT_ERROR = "Cannot create Match : ";

    public String createMatch(String homeTeam, String awayTeam) {
        // Input validation
        if (homeTeam == null || awayTeam == null) {
            return TXT_ERROR+"Team names cannot be null";
        }
        else if(homeTeam==awayTeam)
            return TXT_ERROR+"Both team names are same.";

        // Generate unique match ID without synchronization
        String matchId = generateMatchId();

        // Create and put the match details into the scoreboard
        WorldCupFixtureBoard.scoreBoard.put(matchId, new MatchInfo(homeTeam, awayTeam, 0, 0, gameSequence));

        return matchId;
    }

    // A synchronized method to generate a unique match ID
    private synchronized String generateMatchId() {
        return WorldCupFixtureBoard.MATCH + ++gameSequence;
    }
}

