// web/src/components/BettingHistory.jsx
import "../css/BettingHistory.css";

const BettingHistory = ({ bets, onRemoveBet }) => {
  if (!bets || bets.length === 0) {
    return <h4>No bets found in your history.</h4>;
  }

  return (
    <div className="betting-history">
      {bets.map((bet, index) => (
        <div key={index} className="bet-record">
          <div className="bet-logos">
            <img
              src={bet.teamAwayLogo}
              alt="away"
              className="bet-team-away-logo-large"
            />
            <span className="bet-at-symbol">@</span>
            <img
              src={bet.teamHomeLogo}
              alt="home"
              className="bet-team-home-logo-large"
            />
          </div>
          <div className="bet-event-details">
            <p>Event: {bet.eventName}</p>
            <p>Date: {bet.eventDate}</p>
            <p>Status: {bet.eventStatus}</p>
          </div>
          <div className="bet-details">
            <p>Team Bet On: {bet.teamBetOn}</p>
            <p>Market: {bet.bettingMarket}</p>
            <p>Amount Wagered: ${bet.amountWagered}</p>
            <p>Odds: {bet.odds}</p>
            {bet.projection && <p>Projection: {bet.projection}</p>}
            {bet.bookmakerId && <p>Bookmaker: {bet.bookmakerId}</p>}
            <p>Date Placed: {new Date(bet.datePlaced).toLocaleString()}</p>
          </div>
          <div className="bet-result">
            <p>Result: {bet.gainOrLoss > 0 ? "Win" : bet.gainOrLoss < 0 ? "Loss" : "Pending"}</p>
            {bet.gainOrLoss !== 0 && <p>Gain/Loss: ${bet.gainOrLoss}</p>}
            {bet.eventStatus === "Final" && (
              <div>
                <img
                  src={bet.teamAwayLogo}
                  alt="away"
                  className="bet-team-away-logo-small"
                />
                <span className="bet-winner-symbol">{bet.scoreAway}</span>
                <span className="bet-winner-symbol">-</span>
                <span className="bet-winner-symbol">{bet.scoreHome}</span>
                <img
                  src={bet.teamHomeLogo}
                  alt="home"
                  className="bet-team-home-logo-small"
                />
                <p>Winner: {bet.teamWinner}</p>
              </div>
            )}
          </div>
          <div className="bet-remove-button">
            <button
              className="button remove-bet"
              onClick={() => onRemoveBet(index)}
            >
              X
            </button>
          </div>
        </div>
      ))}
    </div>
  );
};

export default BettingHistory;