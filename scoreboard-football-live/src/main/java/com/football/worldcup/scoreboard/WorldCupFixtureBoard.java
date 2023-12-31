package com.football.worldcup.scoreboard;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class WorldCupFixtureBoard {

    private static ConcurrentHashMap<String, MatchInfo> scoreBoard = new ConcurrentHashMap<String, MatchInfo>();;
    private static int gameSequence = 0;
    private static final String MATCH = "Match";
    private static final String TXT_CREATE_ERROR = "Cannot create Match : ";
    private static final String TXT_UPDATE_ERROR = "Cannot update Match : ";
    private static final String TXT_FINISH_ERROR = "Cannot end Match : ";

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
            matchDetail.setHomeTeamGoals(homeGoals);
            matchDetail.setAwayTeamGoals(awayGoals);
            return "Hurray..!!! - Match updated Successfully for matchID - "+matchID;
        }

    }

    public String finishMatch(String matchId) {
        if(matchId==null)
        {
            return TXT_FINISH_ERROR + "MatchId cannot be NULL";
        }
        if (WorldCupFixtureBoard.scoreBoard.containsKey(matchId))
        {
            WorldCupFixtureBoard.scoreBoard.remove(matchId);
        }
        else
            return TXT_FINISH_ERROR+"No MatchId found for this match.";
        return matchId + " has been finished successfully";
    }

    public List<MatchInfo> getSummary()
    {
        // Create a copy of the list to avoid modifying the original list
        List<MatchInfo> summary = new ArrayList<MatchInfo>(WorldCupFixtureBoard.scoreBoard.values());

        // Sort the summary list based on total score and match order
        Collections.sort(summary, new Comparator<MatchInfo>() {
            public int compare(MatchInfo a, MatchInfo b) {
                int totalGoalComparison = Integer.compare(
                        b.getHomeTeamGoals() + b.getAwayTeamGoals(),
                        a.getHomeTeamGoals() + a.getAwayTeamGoals()
                );

                if (totalGoalComparison == 0) {
                    return Integer.compare(a.getGameSequence(), b.getGameSequence());
                }

                return totalGoalComparison;
            }
        });

        return summary;
    }
}

