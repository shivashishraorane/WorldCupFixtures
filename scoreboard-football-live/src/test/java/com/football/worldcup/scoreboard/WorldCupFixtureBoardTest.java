package com.football.worldcup.scoreboard;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Hashtable;


public class WorldCupFixtureBoardTest {

    public static WorldCupFixtureBoard footballWorldCupScoreBoard;
    public final static String TXT_ERROR ="Cannot create match : ";

    @Before
    public void setUp() throws Exception {

        WorldCupFixtureBoardTest.footballWorldCupScoreBoard = new WorldCupFixtureBoard();
        ReflectionTestUtils.setField(WorldCupFixtureBoard.class, "gameSequence", 0);
        ReflectionTestUtils.setField(WorldCupFixtureBoard.class, "scoreBoard",
                new Hashtable<String, MatchInfo>());
        footballWorldCupScoreBoard.createMatch("Argentina", "France");
        footballWorldCupScoreBoard.createMatch("Croatia", "Germany");
        footballWorldCupScoreBoard.createMatch("Qatar", "Brazil");
    }

    @Test
    public void test_teamNames_isNull()
    {
        assertTrue(footballWorldCupScoreBoard.createMatch(null, null).equals(TXT_ERROR+"Team names cannot be null."));
    }

    @Test
    public void test_sameTeamNames()
    {
        assertTrue(footballWorldCupScoreBoard.createMatch("Argentina","Argentina").equals(TXT_ERROR+"Both team names are same."));
    }

    @Test
    public void testCreateGame_add_new_game() {

        footballWorldCupScoreBoard.createMatch("Argentina", "Australia");
        Hashtable<String, MatchInfo> scoreBoard = (Hashtable<String, MatchInfo>) ReflectionTestUtils.getField(WorldCupFixtureBoard.class,"gameSequence");
        assertTrue(scoreBoard.size() == 4);
    }
}

