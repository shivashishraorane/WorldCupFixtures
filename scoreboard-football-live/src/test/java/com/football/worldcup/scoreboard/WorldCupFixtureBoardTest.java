package com.football.worldcup.scoreboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.concurrent.ConcurrentHashMap;


public class WorldCupFixtureBoardTest {

    public static WorldCupFixtureBoard footballWorldCupScoreBoard;
    public final static String TXT_CREATE_ERROR ="Cannot create match : ";
    public final static String TXT_UPDATE_ERROR ="Cannot update match : ";

    @Before
    public void setUp() throws Exception {

        WorldCupFixtureBoardTest.footballWorldCupScoreBoard = new WorldCupFixtureBoard();
        ReflectionTestUtils.setField(WorldCupFixtureBoard.class, "gameSequence", 0);
        ReflectionTestUtils.setField(WorldCupFixtureBoard.class, "scoreBoard",
                new ConcurrentHashMap<String, MatchInfo>());
        footballWorldCupScoreBoard.createMatch("Argentina", "France");
        footballWorldCupScoreBoard.createMatch("Croatia", "Germany");
        footballWorldCupScoreBoard.createMatch("Qatar", "Brazil");
    }

    @Test
    public void test_teamNames_isNull()
    {
        assertTrue(footballWorldCupScoreBoard.createMatch(null, null).equals(TXT_CREATE_ERROR+"Team names cannot be null."));
    }

    @Test
    public void test_sameTeamNames()
    {
        assertTrue(footballWorldCupScoreBoard.createMatch("Argentina","Argentina").equals(TXT_CREATE_ERROR+"Both team names are same."));
    }

    @Test
    public void test_add_newMatch()
    {
        footballWorldCupScoreBoard.createMatch("Argentina", "Qatar");
        ConcurrentHashMap<String, MatchInfo> scoreBoard = (ConcurrentHashMap<String, MatchInfo>) ReflectionTestUtils.getField(WorldCupFixtureBoard.class,"gameSequence");
        assertTrue(scoreBoard.size() == 4);
    }

    @Test
    public void test_update_currentMatch_negativeGoals()
    {
        assertTrue(footballWorldCupScoreBoard.updateMatch("Match1", -1, 0).equals(TXT_UPDATE_ERROR+"Negative Goals cannot be scored"));
    }

    @Test
    public void test_update_curentMatch_invalidMatchId()
    {
        assertTrue(footballWorldCupScoreBoard.updateMatch("Match777",1,4).equals(TXT_UPDATE_ERROR+"MatchId not found."));
    }

    @Test
    public void test_update_curentMatch_validGoals_and_MatchId()
    {
        assertTrue(footballWorldCupScoreBoard.updateMatch("Match1",1,4).equals("Hurray..!!! - Match updated Successfully"));
    }


}

