# World Cup Fixture Board

Live Scoreboard for Football WorldCup 2022

# Overview
The Live Football World Cup Scoreboard is a Java-based application developed for a sports data company. It provides information about football matches, including match details, goals, and its summaries. This project allows users to create and update football match data and view a summary of matches.

# Features
Create football matches with home and away teams.  

Update match scores in real time.  

End an ongoing match.  

View a summary of matches sorted by game order or game sequence.  

# Tech Stack

Java  
Junit  

# Usage

1.) Creating a match -  
		createMatch()- method to add the new match with initial scores (0,0) and add it to Scoreboard.  
		
2.) generateMatchId() - method to generate unique matchId for a match.  

3.) Update the Match Details -  
		updateMatch() - method to update the goals scored by any team (home or away) in a given match for its associated matchId.  
		
4.) End the Match -   
		finishMatch() - method to end the match and remove it from the Scoreboard.  
		
5.) Summary of Match -   
		getSummary() - method to get the summary for the matches from initial State (when match just started and goals were (0,0)) and from updated scores (after any changes in goals).  
		

# Extras

1.) WorldCupFixtureBoardTest - It conatins all the test cases for this application.


