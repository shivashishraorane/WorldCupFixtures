package com.football.worldcup.scoreboard;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.junit.Before;
import org.junit.Test;

import org.springframework.test.util.ReflectionTestUtils;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;


public class WorldCupFixtureBoardTest {

    public static WorldCupFixtureBoard footballWorldCupScoreBoard;
    public final static String TXT_CREATE_ERROR ="Cannot create match : ";
    public final static String TXT_UPDATE_ERROR ="Cannot update match : ";
    public final static String TXT_FINISH_ERROR ="Cannot end match : ";
    public final static String TXT_SUMMARY_ERROR ="Cannot get summary";
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

    @Test
    public void test_update_currentMatch_nullMatchId()
    {
        String result = footballWorldCupScoreBoard.updateMatch(null, 3, 4);
        assertNotNull(result);
    }

    @Test
    public void test_update_currentMatch_nullMatchDetail()
    {
        assertTrue(footballWorldCupScoreBoard.updateMatch("MissingMatchID",2,5).equals(TXT_UPDATE_ERROR+"No match details found for the associated matchId "));
    }

    @Test
    public void test_finishMatch_NullMatchID()
    {
        assertTrue(footballWorldCupScoreBoard.finishMatch(null).equals(TXT_FINISH_ERROR+" MatchId cannot be NULL"));
    }

    @Test
    public void test_finishMatch_NonExistent_MatchID()
    {
        assertTrue(footballWorldCupScoreBoard.finishMatch("MissingMatchID").equals(TXT_FINISH_ERROR+"No MatchId found for this case"));
    }

    @Test
    public void test_finishMatch_success()
    {
        assertTrue(footballWorldCupScoreBoard.finishMatch("Match1").equals("Match1 has been finished successfully"));
        //checking if the match ended for the associated matchID
        ConcurrentHashMap<String, MatchInfo> scoreBoard = (ConcurrentHashMap<String, MatchInfo>) ReflectionTestUtils.getField(WorldCupFixtureBoard.class,"scoreBoard");
        assertFalse(scoreBoard.containsKey("Match1"));
    }
    @Test
    public void test_getSummary_initialState() {
        // Gets the summary of matches when no update was made (showing initial scores (0,0))
        List<MatchInfo> summary = footballWorldCupScoreBoard.getSummary();

        // Check the order of matches in the initial state(order check can be set as per requirement)
        assertEquals("Match1", summary.get(0).getGameSequence());
        assertEquals("Match2", summary.get(1).getGameSequence());
        assertEquals("Match3", summary.get(2).getGameSequence());
        assertEquals("Match4", summary.get(3).getGameSequence());
    }

    @Test
    public void test_getSummary_updatedGoalsState() {
        // Update the score of "Match1" to 4-3
        footballWorldCupScoreBoard.updateMatch("Match3", 4, 3);

        // Get the summary of matches after goals are updated to it.
        List<MatchInfo> summary = footballWorldCupScoreBoard.getSummary();

        //Check the order of matches after the score update based on game order (order can be set as per requirement).
        assertEquals("Match3", summary.get(0).getGameSequence());
        assertEquals("Match4", summary.get(1).getGameSequence());
        assertEquals("Match2", summary.get(2).getGameSequence());
        assertEquals("Match1", summary.get(3).getGameSequence());
    }
}

