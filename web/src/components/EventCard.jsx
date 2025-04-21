// Focuses on rendering a single event card and handling its own logic (like betting drop downs)
// Can be reused anywhere I need to display an event

import { useState } from "react";
import "../css/EventCard.css";
import "../css/mobile/EventCardMobile.css"
import "../css/BettingButtons.css";
import "../css/mobile/BettingButtonsMobile.css";

const EventCard = ({ event, onAddBet }) => {
  const [openDropdown, setOpenDropdown] = useState(null);
  const [betData, setBetData] = useState({
    amountWagered: "",
    odds: "",
    projection: "",
    bookmaker: "",
  });
  const [error, setError] = useState("");

  const toggleDropdown = (market, team) => {
    const key = `${team}-${market}`;
    setOpenDropdown(openDropdown === key ? null : key);
    setError("");
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBetData((prev) => ({ ...prev, [name]: value }));
  };

  const submitBet = (market, teamBetOn) => {
    if (!betData.amountWagered || !betData.odds) {
      setError("Amount and odds are required.");
      return;
    }
    if (isNaN(parseFloat(betData.amountWagered)) || isNaN(parseFloat(betData.odds))) {
      setError("Amount and odds must be numbers.");
      return;
    }

    const bet = {
      eventId: event.eventId,
      eventName: event.eventName,
      eventDate: event.eventDate,
      eventStatus: event.eventStatus,
      teamHome: event.teamHome,
      teamAway: event.teamAway,
      scoreHome: event.scoreHome,
      scoreAway: event.scoreAway,
      teamHomeLogo: event.teamHomeLogo,
      teamAwayLogo: event.teamAwayLogo,
      teamWinner: event.teamWinner || "-",
      amountWagered: parseFloat(betData.amountWagered),
      odds: parseFloat(betData.odds),
      projection: betData.projection || "",
      bookmakerId: betData.bookmaker || "",
      bettingMarket: market,
      teamBetOn: teamBetOn === event.teamHome ? "Home Team" : "Away Team",
      datePlaced: new Date().toISOString(),
      gainOrLoss: 0,
      scoreTotal: (parseInt(event.scoreHome) || 0) + (parseInt(event.scoreAway) || 0),
    };
    onAddBet(bet);
    setBetData({ amountWagered: "", odds: "", projection: "", bookmaker: "" });
    setOpenDropdown(null);
  };

  const renderBettingButtons = (team) => {
    const markets = ["Moneyline", "Spread", "Total"];
    return markets.map((market) => (
      <div key={`${team}-${market}`} className="bet-button">
        <button
          onClick={() => toggleDropdown(market, team)}
          className={`button ${team === event.teamHome ? "home" : "away"}`}
          id={`event-${team}-${market.toLowerCase()}`}
        >
          {market === "Moneyline" ? "ML" : market}
          <div className="hover-indicator-betting"></div>
        </button>
        {openDropdown === `${team}-${market}` && (
          <div className={`${market.toLowerCase()}-dropdown-content absolute left-0 mt-1 w-20 bg-black/80 p-2 rounded shadow-lg z-10`}>
            <input
              name="amountWagered"
              value={betData.amountWagered}
              onChange={handleInputChange}
              placeholder="Amount"
              className="amount-wagered w-full mb-1 text-white bg-transparent border-b text-sm"
            />
            <input
              name="odds"
              value={betData.odds}
              onChange={handleInputChange}
              placeholder="Odds"
              className="odds w-full mb-1 text-white bg-transparent border-b text-sm"
            />
            <input
              name="projection"
              value={betData.projection}
              onChange={handleInputChange}
              placeholder="O/U, Sprd."
              className="projection w-full mb-1 text-white bg-transparent border-b text-sm"
            />
            <input
              name="bookmaker"
              value={betData.bookmaker}
              onChange={handleInputChange}
              placeholder="Bookie"
              className="bookmaker w-full mb-1 text-white bg-transparent border-b text-sm"
            />
            {error && <p className="text-red-500 text-xs mb-1">{error}</p>}
            <label className="checkmark" onClick={() => submitBet(market, team)}></label>
          </div>
        )}
      </div>
    ));
  };

  return (
    <div
      className="event-card"
      style={{
        "--home-color": `#${event.teamHomeColor}`,
        "--away-color": `#${event.teamAwayColor}`,
        "--home-color-alt": `#${event.teamHomeColorAlt}`,
        "--away-color-alt": `#${event.teamAwayColorAlt}`,
      }}
    >
      <div className="event-data" style={{ display: "none" }}>
        {/* Hidden data attributes */}
      </div>
      <div className="event-container">
        <div className="betting-buttons-container away">
          {renderBettingButtons(event.teamAway)}
        </div>
        <div className="event-stats">
          {event.eventStatusId.includes("2") ? (
            // Live Event
            <>
              <div className="team-details">
                <span className="team-name-text">{event.teamAwayNameAbr}</span>
                <img src={event.teamAwayLogo} alt="away" className="event-team-logo-away" />
                <span className="score-text">{event.scoreAway}</span>
              </div>
              <div className="event-status">
                <div className="live-text">
                  <span className="live-circle"></span>
                  LIVE
                </div>
                <span className="event-status-text">{event.eventStatus}</span>
              </div>
              <div className="team-details">
                <span className="team-name-text">{event.teamHomeNameAbr}</span>
                <img src={event.teamHomeLogo} alt="home" className="event-team-logo-home" />
                <span className="score-text">{event.scoreHome}</span>
              </div>
            </>
          ) : event.eventStatusId.includes("3") ? (
            // Final
            <>
              <div className="team-details">
                <span className="team-name-text">{event.teamAwayNameAbr}</span>
                <img src={event.teamAwayLogo} alt="away" className="event-team-logo-away" />
                <span className="score-text">{event.scoreAway}</span>
              </div>
              <div className="event-status">
                <span className="team-winner-text">{event.teamWinner}</span>
                <span className="event-status-text">{event.eventStatus}</span>
              </div>
              <div className="team-details">
                <span className="team-name-text">{event.teamHomeNameAbr}</span>
                <img src={event.teamHomeLogo} alt="home" className="event-team-logo-home" />
                <span className="score-text">{event.scoreHome}</span>
              </div>
            </>
          ) : (
            // Scheduled
            <>
              <div className="team-details">
                <span className="team-name-text">{event.teamAwayNameAbr}</span>
                <img src={event.teamAwayLogo} alt="away" className="event-team-logo-away" />
              </div>
              <div className="event-status">
                <span className="event-status-text">{event.eventStatus}</span>
              </div>
              <div className="team-details">
                <span className="team-name-text">{event.teamHomeNameAbr}</span>
                <img src={event.teamHomeLogo} alt="home" className="event-team-logo-home" />
              </div>
            </>
          )}
        </div>
        <div className="betting-buttons-container home">
          {renderBettingButtons(event.teamHome)}
        </div>
      </div>
      <div className="event-details">
        <div className="hover-indicator"></div>
        <span className="event-name-text">{event.eventName}</span>
        <span className="event-date-text">{event.eventDate}</span>
      </div>
    </div>
  );
};

export default EventCard;