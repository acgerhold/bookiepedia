## Bookiepedia Design

## 1. Problem Statement

For sports fans, it might feel easier to bet at one sportsbook. Your money is all in one place, and you know exactly where to go to find what you want to bet and how those bets perform. However, utilizing multiple sportsbooks allows a bettor to shop for the best odds, similar to comparing prices across multiple stores when making a big purchase. While shopping odds helps in getting more value for your bets, it can be disorganized having to spread your activity across each bookmaker's app or service leading to missed opportunities. Bookiepedia aims to reduce the amount of time spent cycling through each bookmaker's service for updates on odds and your bets by consolidating that information into all into one place. Users will be able to monitor odds from major US bookmakers and record bets they intend to make on each platform. Recorded bets will be updated automatically when they hit or miss and contribute to helpful analytics about a user's betting activity. These insights will be presented to the user when viewing their betting activity where they can further sort, filter, and organize the data.

## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that you might like help working through._

1. How often should I send a request to The Odds API to refresh odds for games? Every hour, refresh button, on/off switch?
2. 
3. 

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

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure you are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle in your design._

### 4.1. In Scope

_Which parts of the problem defined in Sections 1 and 2 will you solve with this design? This should include the base functionality of your product. What pieces are required for your product to work?_

_The functionality described above should be what your design is focused on. You do not need to include the design for any out of scope features or expansions._

* This application will only cover the NBA and NHL because they are two of the main in-season sports currently. 
* It will only cover head-to-head, spreads, and totals betting markets. 
* Upcoming games/events within 30 days of the current date will be listed for a selected league. 
* Only US bookmaker's odds will be displayed. 
* Not every past game will have reports generated showing change in bookmaker odds.

### 4.2. Out of Scope

_Based on your problem description in Sections 1 and 2, are there any aspects you are not planning to solve? Do potential expansions or related problems occur to you that you want to explicitly say you are not worrying about now? Feel free to put anything here that you think your team can't accomplish in the unit, but would love to do with more time._

_The functionality here does not need to be accounted for in your design._

* Implement helpful tools and point out trends in bookmaker odds to support strategies like "arbitrage betting". 
* Use a dashboard tool like Tableau to create the reports on bookmaker's odds trends throughout the course of a game and insights on user's betting activity.
* Other betting markets like player props
* Leagues outside the NBA and NHL

# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include class diagram(s) showing what components you are planning to build. You should argue why this architecture (organization of components) is reasonable. That is, why it represents a good data flow and a good separation of concerns. Where applicable, argue why this architecture satisfies the stated requirements._

The MLP will include creating detailed bet slips that are saved to a user's betting record. Creating bet slips and updating or returning a user's betting record will be achieved using API Gateway and Lambda to create several endpoints. Data for US bookmaker's odds and upcoming games/events will be acquired using the ESPN API and The Odds API that can also be returned via endpoints. The application will utilize DynamoDB for data warehousing and GSIs for efficient data retrieval. The application's web interface will allow users to manage their bets slips and view helpful insights about their betting habits.

# 6. API

## 6.1. Public Models

_Define the data models your service will expose in its responses via your *`-Model`* package. These will be equivalent to the *`PlaylistModel`* and *`SongModel`* from the Unit 3 project._

* EventModel
* OddsModel
* BetModel
* RecordModel

## 6.2. _Get Events Endpoint_

_Describe the behavior of the first endpoint you will build into your service API. This should include what data it requires, what data it returns, and how it will handle any known failure cases. You should also include a sequence diagram showing how a user interaction goes from user to website to service to database, and back. This first endpoint can serve as a template for subsequent endpoints. (If there is a significant difference on a subsequent endpoint, review that with your team before building it!)_

_(You should have a separate section for each of the endpoints you are expecting to build...)_

## 6.3 _Get User Record Endpoint_

_(repeat, but you can use shorthand here, indicating what is different, likely primarily the data in/out and error conditions. If the sequence diagram is nearly identical, you can say in a few words how it is the same/different from the first endpoint)_

## 6.4 _Create Bet Endpoint_

## 6.5 _Add Bet to Record Endpoint_

## 6.6 _Update Bet in User Record Endpoint_

# 7. Tables

_Define the DynamoDB tables you will need for the data your service will use. It may be helpful to first think of what objects your service will need, then translate that to a table structure, like with the *`Playlist` POJO* versus the `playlists` table in the Unit 3 project._

# 8. Pages

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_