## Bookiepedia Design

## 1. Problem Statement

For sports fans, it might feel easier to bet at one sportsbook. Your money is all in one place, and you know exactly where to go to find what you want to bet and how those bets perform. However, utilizing multiple sportsbooks allows a bettor to shop for the best odds, similar to comparing prices across multiple stores when making a big purchase. While shopping odds helps in getting more value for your bets, it can be disorganized having to spread your activity across each bookmaker's app or service leading to missed opportunities. Bookiepedia aims to reduce the amount of time spent cycling through each bookmaker's service for updates on odds and your bets by consolidating that information into all into one place. Users will be able to monitor odds from major US bookmakers and record bets they intend to make on each platform. Recorded bets will be updated automatically when they hit or miss and contribute to helpful analytics about a user's betting activity. These insights will be presented to the user when viewing their betting activity where they can further sort, filter, and organize the data.

## 2. Top Questions to Resolve in Review

1. How often should I send a request to The Odds API to refresh odds for games? Every hour, refresh button, on/off switch?
2. How can I refresh the API calls in certain time intervals?
3. How will I hide my API key for the Odds API? Can users enter their own key?

## 3. Use Cases

### As a user, I'd like to:

U1. View upcoming games/events in the next 30 days

U2. Filter upcoming games/events by league

U3. View the main three betting markets for each game: head-to-head, spreads, and totals (aka money line, points handicap, over/under)

U4. View major US bookmaker's odds for each of the three markets

U5. View reports that illustrate how a bookmaker's odds changed over the duration of a past game

U6. Save a detailed record of each bet placed

U7. Have my betting slips updated to represent bets that hit or missed

U8. View my betting history with ability to sort and filter data

U9. View performance metrics for my betting history based on how user has sorted and/or filtered the data (profit +/-, trends)

## 4. Project Scope

### 4.1. In Scope

* Events for the NBA and NHL through the ESPN API
* Retrieve odds for money line, spread, and total betting markets from The Odds API
* Adding to and retrieving a list of user's bets placed
* Present brief summary of user's betting trends

### 4.2. Out of Scope

* Any other betting market outside of money line, spread, and total
* Leagues outside the NBA and NHL
* Dashboards made using Tableau to embed

# 5. Proposed Architecture Overview

The MLP will include creating detailed bet slips that are saved to a user's betting history.
Creating, updating, or returning a user's betting history will be achieved using API Gateway and Lambda to
create several endpoints. Data for US bookmaker's odds and upcoming games/events will be acquired using the
ESPN API and The Odds API that can also be returned via endpoints. The application will utilize DynamoDB for
data warehousing and GSIs for efficient data retrieval. The application's web interface will allow users to
manage their bets slips and view helpful insights about their bets.

# 6. API

## 6.1. Public Models

* ScheduleModel
  * scheduleId : String
  * leagueId : String
  * leagueName : String
  * timestamp : String
  * eventIdList : List<String>
* EventModel
  * eventId : String
  * eventName : String
  * eventNameShort : String
  * eventHeadline : String
  * leagueId : String
  * eventDate : String
  * eventSeasonId : String
  * teamHome : String
  * teamAway : String
  * eventStatusId : String
  * eventStatus : String
  * teamWinner : String
  * scoreHome : Integer
  * scoreAway : Integer
  * scoreTotal : Integer
  * links : List<String>
* LeagueModel
  * leagueId : String
  * seasonStatusId : String
  * seasonStatus : String
  * seasonYear : String
  * leagueLogo : String
* TeamModel
  * leagueId : String
  * teamId : String
  * teamName : String
  * teamNameAbr : String
  * teamLogo : String
  * teamColor : String
  * teamAlternateColor : String
  * teamLinks : List<String>
* HistoryModel
* BetModel

## 6.2. _Get Schedule Endpoint_

* Accepts GET requests to /schedule/:leagueId
  * Accepts a league ID and will return a ScheduleModel containing a list of events for the corresponding league
    * Throws ScheduleDataQualityException if % of valid fields is below given threshold

## 6.3 _Get Events in Schedule Endpoint_

* Accepts GET requests to /schedule/:scheduleId/events
  * Accepts a scheduleId and retrieves corresponding schedule's list of events
    * Throws EventDataQualityException if % of valid fields is below given threshold

## 6.4 _Get Odds Endpoint_

* Accepts GET requests to /events/:eventId/odds
  * Accepts an eventId and retrieves odds for money line, spread, and total markets @ time of request
    * Throws NoAvailableOddsException (TBD)

## 6.5 _Add Bet to History Endpoint_

* Accepts POST requests to /history/:userID/bets
  * Accepts a userId and BetModel to be added to user's betting history
    * Throws InvalidBetException (TBD)

## 6.6 _Update Bet in User Record Endpoint_

* Accepts PUT requests to /history/:userId/bets
  * Accepts a user ID and will update a user's bet in their betting history
    * Used when an event has concluded to update a bet as hit or miss

# 7. Tables

* Schedule
  * scheduleId : String (Partition)
  * leagueId : String (Hash)
  * leagueName : String
  * timestamp : String
  * eventIdList : List<String>
* Event
  * eventId : String (Partition)
  * eventName : String
  * eventNameShort : String
  * eventHeadline : String
  * leagueId : String
  * eventDate : String (Hash)
  * eventSeasonId : String
  * teamHome : String
  * teamAway : String
  * eventStatusId : String
  * eventStatus : String
  * teamWinner : String
  * scoreHome : Integer
  * scoreAway : Integer
  * scoreTotal : Integer
  * links : List<String>
* League
  * leagueId : String (Partition)
  * seasonStatusId : String
  * seasonStatus : String
  * seasonYear : String
  * leagueLogo : String
* Team
  * teamId : String (Partition)
  * leagueId : String (Hash)
  * teamName : String
  * teamNameAbr : String
  * teamLogo : String
  * teamColor : String
  * teamAlternateColor : String
  * teamLinks : List<String>
* History
* Bet

# 8. Pages

![image](https://private-user-images.githubusercontent.com/90284841/332282772-c8c9f6f1-7851-4b40-8239-3cb8d4bc5b2d.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTYyNjY2MzcsIm5iZiI6MTcxNjI2NjMzNywicGF0aCI6Ii85MDI4NDg0MS8zMzIyODI3NzItYzhjOWY2ZjEtNzg1MS00YjQwLTgyMzktM2NiOGQ0YmM1YjJkLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA1MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNTIxVDA0Mzg1N1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPWIwNjMyZWIzZGMxOThjNTBhZWMxMWQzZjdiNTE0YTFmMzIzYWQxMjY0YTAxMTllMGZiN2MxOTYyOTNiOTYxMzImWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.IMehKvYN0md5iokN6TU1UeJOR9D2iQCytmY3BKoyABw)
Main screen showing upcoming games for selected league. Users place bets by clicking "Bet" button, selecting the type of bet they want to make, and enter the amount.
![image](https://private-user-images.githubusercontent.com/90284841/332282843-79a3fe08-4325-4e2b-b9f2-798058f20266.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTYyNjY2MzcsIm5iZiI6MTcxNjI2NjMzNywicGF0aCI6Ii85MDI4NDg0MS8zMzIyODI4NDMtNzlhM2ZlMDgtNDMyNS00ZTJiLWI5ZjItNzk4MDU4ZjIwMjY2LnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA1MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNTIxVDA0Mzg1N1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTdlYzU3NDhlMzdhMDNhMWM5Y2JhNWFlNWQzN2U3Y2ZlMzNlNmJlMzk3OGZkMWY1ZWE0ZDQxZTY3M2E5ODBmMDcmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.cCJRTJLJNaphccvmRGyVULFhCLQZSib53DtYk9uiT3M)
User's betting history UI with newly added bet from image 1.
![image](https://private-user-images.githubusercontent.com/90284841/332283069-d64c9efd-3848-4831-9ba5-19945e93969e.png?jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJnaXRodWIuY29tIiwiYXVkIjoicmF3LmdpdGh1YnVzZXJjb250ZW50LmNvbSIsImtleSI6ImtleTUiLCJleHAiOjE3MTYyNjcxMjcsIm5iZiI6MTcxNjI2NjgyNywicGF0aCI6Ii85MDI4NDg0MS8zMzIyODMwNjktZDY0YzllZmQtMzg0OC00ODMxLTliYTUtMTk5NDVlOTM5NjllLnBuZz9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPUFLSUFWQ09EWUxTQTUzUFFLNFpBJTJGMjAyNDA1MjElMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjQwNTIxVDA0NDcwN1omWC1BbXotRXhwaXJlcz0zMDAmWC1BbXotU2lnbmF0dXJlPTkzZDk0ZTI1YzUzNjhlNTI3ZGY5MjQyNmYxMDI5NTYyMDYyZDFiZGE3ZGNkZDYxMzNjMzUzMjJkYzlhMDRjMjAmWC1BbXotU2lnbmVkSGVhZGVycz1ob3N0JmFjdG9yX2lkPTAma2V5X2lkPTAmcmVwb19pZD0wIn0.-W4jIaGoJRyBDe9CcYpvOyBNaRTILYizSwdjSJVo5ks)
Analytics tab showing insights on user's betting history. Includes links to filter and redirect to history tab.