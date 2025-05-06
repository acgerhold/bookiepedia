// New top-level component responsible for:

// Fetching data from BookiepediaClient
// Managing the apps state (current schedule, list of events)
// Rendering the league selector, buttons, and list of event cards
// Passing data to child components (EventCard.jsx)

// Focuses on fetching data and rendering the overall page structure

import { useEffect, useState } from "react";
import BookiepediaClient from "../api/bookiepediaClient";
import Header from "../components/Header";
import EventCard from "../components/EventCard";
import "../css/LeagueCard.css";
import "../css/mobile/LeagueCardMobile.css";
import "../css/LeaguesCard.css";

// Dynamically import all .png files from the folder
const icons = import.meta.glob("../assets/buttons/sports/*.png", { eager: true });

// Convert the imported files into an object with file names as keys
const iconPaths = Object.keys(icons).reduce((acc, path) => {
  const fileName = path.split("/").pop().replace(".png", ""); // Extract file name without extension
  acc[fileName] = icons[path].default || icons[path]; // Use `.default` for Vite's eager imports
  return acc;
}, {});

const GetEvents = () => {
  const [client, setClient] = useState(null);
  const [sport, setSport] = useState(null);
  const [leagues, setLeagues] = useState([]);
  const [schedule, setSchedule] = useState(null);
  const [events, setEvents] = useState([]);
  const [loading, setLoading] = useState(false); 

  useEffect(() => {
    const initClient = async () => {
      const newClient = new BookiepediaClient();
      setClient(newClient);
    };
    initClient();
  }, []);

  const getSchedule = async (league) => {
    if (!client || !league) return;
    setLoading(true); // Start loading
    try {
      const scheduleData = await client.getSchedule(league.id);
      const eventData = await client.getEventsForSchedule(scheduleData.scheduleId);
      setSchedule(scheduleData);
      setEvents(eventData);
    } catch (error) {
      console.error("Error fetching schedule for league", league);
    } finally {
      setLoading(false); // End loading
    }
  };

  const fetchSchedule = async (id, sportName, leagueName) => {
    if (!client) return;
    setLoading(true);
    try {
      await client.fetchSchedule(id, sportName, leagueName);
    } catch (error) {
      console.error("Error fetching schedule: ", error);
    } finally {
      setLoading(false);
    }     
  };

  const getBetsForHistory = async (weeklyHistoryId) => {
    if (client) await client.getBetsForHistory(weeklyHistoryId);
  };

  const handleAddBet = async (bet) => {
    if (client) await client.addBetToHistory(bet);
  };

  const fetchLeagues = async (sportName) => {
    if (!client) return;
    console.log("Fetching leagues for sport:", sportName); // Log sportName
    setLoading(true);
    try {
      setLeagues([]);
      const response = await client.fetchLeagues(sportName);
      console.log("Leagues response:", response); // Log the full response
      const fetchedLeagues = response.leagueModels;
      console.log("Fetched leagues:", fetchedLeagues);
      setLeagues(fetchedLeagues);
    } catch (error) {
      console.error("Error fetching leagues for sport:", sportName, error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      <Header client={client} />
      <div className="league-container">
        <form className="available-leagues">
          <div className="form-field">
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button basketball"
                  id="basketball"
                  alt="Basketball"
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    fetchLeagues("basketball");
                    setSport("basketball");
                    setSchedule(null);
                    setEvents([]);
                  }}
                ></div>
                <span className="sport-title">Basketball</span>
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button hockey"
                  id="hockey"
                  alt="Hockey"
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    fetchLeagues("hockey");
                    setSport("hockey");
                    setSchedule(null);
                    setEvents([]);
                  }}
                ></div>
                <span className="sport-title">Hockey</span>
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button baseball"
                  id="baseball"
                  alt="Baseball"
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    fetchLeagues("baseball");
                    setSport("baseball");
                    setSchedule(null);
                    setEvents([]);
                  }}
                ></div>
                <span className="sport-title">Baseball</span>
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button mma"
                  id="mma"
                  alt="MMA"
                  style={{ cursor: "pointer" }}
                ></div>
                <span className="sport-title">MMA</span>
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button football"
                  id='football'
                  alt="Football"
                  style={{ cursor: "pointer" }}
                  onClick={() => {
                    fetchLeagues("football");
                    setSport("football");
                    setSchedule(null);
                    setEvents([]);
                  }}
                ></div>
                <span className="sport-title">Football</span>
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div
                  className="sport-button soccer"
                  id='soccer'
                  alt="Soccer"
                  style={{ cursor: "pointer" }}
                ></div>
                <span className="sport-title">Soccer</span>
              </div>
            </div>
          </div>
        </form>
        <form className={`available-leagues-two ${sport || ""} ${leagues.length === 0 ? "hidden" : ""}`}>
          <div className="form-field-two">
            {leagues.length > 0 ? (
              leagues.map((league) => (
                <div 
                  key={league.leagueId} 
                  className="league-item"
                  title={league.leagueNameFull} 
                  style={{ cursor: "pointer" }}
                  onClick={async () => {
                    await fetchSchedule(league.leagueId, league.leagueName, league.sportName);
                    getSchedule({ id: league.leagueId, alt: league.leagueName });
                  }}
                >
                  <img 
                    className="league-button" 
                    src={league.leagueLogo}  
                    alt={league.leagueNameFull} 
                    onError={(e) => {
                      e.target.style.display = "none"; // Hide the broken image
                      e.target.nextSibling.style.display = "inline"; // Show the league name
                    }}
                  />
                  <span className="league-name" style={{ display: "none" }}>{league.leagueNameFull}</span>
                </div>
              ))
            ) : (
              <h4>No Leagues Currently Available</h4>
            )}
          </div>
        </form>
      </div>
      <div className={`events-container ${!schedule ? "hidden" : ""}`}>
        <h3>
          <span className="search-criteria-display">
            {schedule ? `${schedule.scheduleName}` : ""}
          </span>
        </h3>
        <div className="search-results-display">
          {loading ? (
            <h4>Loading events...</h4>
          ) : events.length > 0 ? (
            events.map((event) => (
              <EventCard key={event.eventId} event={event} onAddBet={handleAddBet} />
            ))
          ) : schedule ? (
            <h4>No upcoming events found!</h4>
          ) : (
            <h4>Please select a league to view events.</h4>
          )}
        </div>
      </div>
    </>
  );
};

export default GetEvents;