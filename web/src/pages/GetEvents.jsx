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
import "../css/GetEvents.css";

const GetEvents = () => {
  const [client, setClient] = useState(null);
  const [schedule, setSchedule] = useState(null);
  const [events, setEvents] = useState([]);

  useEffect(() => {
    const initClient = async () => {
      const newClient = new BookiepediaClient();
      setClient(newClient);
    };
    initClient();
  }, []);

  const getSchedule = async (league) => {
    if (!client || !league) return;
    const scheduleData = await client.getSchedule(league.id);
    const eventData = await client.getEventsForSchedule(scheduleData.scheduleId);
    setSchedule(scheduleData);
    setEvents(eventData);
  };

  const fetchSchedule = async () => {
    if (client) await client.fetchSchedule();
  };

  const getBetsForHistory = async (weeklyHistoryId) => {
    if (client) await client.getBetsForHistory(weeklyHistoryId);
  };

  const handleAddBet = async (bet) => {
    if (client) await client.addBetToHistory(bet);
  };

  return (
    <>
      <Header client={client} />
      <div className="card">
        <h2>Select a League to View Live Events & Place Bets</h2>
        <button id="refresh-button" className="refresh-button" onClick={fetchSchedule}>
          Update Events
        </button>
        <button
          id="WH-2024-06-4"
          className="weekly-history"
          onClick={() => getBetsForHistory("WH-2024-06-4")}
        >
          Bet History
        </button>
        <form className="card-content">
          <div className="form-field">
            <div className="icon-container">
              <div className="icon-wrapper">
                <div className="hover-indicator"></div>
                <img
                  className="test-icon"
                  id="46"
                  src="https://a.espncdn.com/combiner/i?img=/i/teamlogos/leagues/500-dark/nba.png&w=500&h=500&transparent=true"
                  alt="NBA"
                  style={{ cursor: "pointer" }}
                  onClick={() => getSchedule({ id: "46", alt: "NBA" })}
                />
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div className="hover-indicator"></div>
                <img
                  className="test-icon"
                  id="90"
                  src="https://a.espncdn.com/i/teamlogos/leagues/500-dark/nhl.png"
                  alt="NHL"
                  style={{ cursor: "pointer" }}
                  onClick={() => getSchedule({ id: "90", alt: "NHL" })}
                />
              </div>
            </div>
            <div className="icon-container">
              <div className="icon-wrapper">
                <div className="hover-indicator"></div>
                <img
                  className="test-icon"
                  id="10"
                  src="https://a.espncdn.com/combiner/i?img=/i/teamlogos/leagues/500-dark/mlb.png&w=500&h=500&transparent=true"
                  alt="MLB"
                  style={{ cursor: "pointer" }}
                  onClick={() => getSchedule({ id: "10", alt: "MLB" })}
                />
              </div>
            </div>
          </div>
        </form>
      </div>
      <div className={`card ${!schedule ? "hidden" : ""}`} id="search-results-container">
        <h3>
          <span id="search-criteria-display">{schedule ? `"${schedule}"` : ""}</span>
        </h3>
        <div id="search-results-display">
          {events.length > 0 ? (
            events.map((event) => (
              <EventCard key={event.eventId} event={event} onAddBet={handleAddBet} />
            ))
          ) : (
            <h4>No upcoming events found!</h4>
          )}
        </div>
      </div>
    </>
  );
};

export default GetEvents;