import BookiepediaClient from '../api/bookiepediaClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


/**
 * Logic needed for the view playlist page of the website.
 */
class GetEvents extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'displaySearchResults', 'getHTMLForSearchResults', 'getSchedule', 'fetchSchedule', 'getEventsForSchedule', 'addBetToHistory', 'getBetsForHistory'], this);

        // Create a enw datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.displaySearchResults);
        console.log("GetEvents constructor");
    }

    /**
     * Add the header to the page and load the BookiepediaClient.
     */
    mount() {
        // Wire up the form's 'submit' event and the button's 'click' event to the search method.
        const icons = document.querySelectorAll('.test-icon')
        icons.forEach(icon => {
            icon.addEventListener('click', (evt) => {
                const leagueId = evt.target.id;
                this.getSchedule(leagueId);
            });
        });

        const refreshButton = document.getElementById('refresh-button');
        refreshButton.addEventListener('click', () => this.fetchSchedule());

        const historyButton = document.querySelector('.weekly-history');
        historyButton.addEventListener('click', (evt) => {
            const weeklyHistoryId = evt.target.id;
            this.getBetsForHistory(weeklyHistoryId);
        });

        this.header.addHeaderToPage();
        this.client = new BookiepediaClient();
    }

    /**
     * Uses the client to perform the search, 
     * then updates the datastore with the criteria and results.
     * @param evt The "event" object representing the user-initiated event that triggered this method.
     */
    async getSchedule(leagueId) {
        // Prevent submitting the from from reloading the page.
        if (!leagueId) {
            return;
        }
        // Add a fetchSchedule() call in here to refresh when a use clicks a league too
        console.log('Retrieving schedule for league ID: ${leagueId}')

        const results = await this.client.getSchedule(leagueId);
        const events = await this.getEventsForSchedule(results);
        console.log('Results:', results);
        this.dataStore.setState({
                        [SEARCH_CRITERIA_KEY]: leagueId,
                        [SEARCH_RESULTS_KEY]: events,
                    });
    }

    async fetchSchedule() {
        console.log('Fetching schedule...')
        try {
            const response = await this.client.fetchSchedule();
            this.dataStore.setState({
//
            [SEARCH_RESULTS_KEY]: response,
            });
        } catch (error) {
            console.error('error fetching schedule', error);
        }
    }

    async getBetsForHistory(weeklyHistoryId) {
        console.log('Retrieving bets...');
        try {
            const response = await this.client.getBetsForHistory(weeklyHistoryId);
            this.dataStore.setState({
            [SEARCH_CRITERIA_KEY]: weeklyHistoryId,
            [SEARCH_RESULTS_KEY]: response,
            });
            console.log('Bets: ', response);
        } catch (error) {
            console.error('error retrieving bets', error);
        }
    }

    async getEventsForSchedule(results) {
        console.log('Retrieving events...');
        try {
            const response = await this.client.getEventsForSchedule(results);
            console.log('Events: ', response);
            return response;
        } catch (error) {
            console.error('error retrieving events', error);
            return [];
        }
    }

    async addBetToHistory(bet) {
        console.log('Adding bet to weekly history...');
        try {
            const response = await this.client.addBetToHistory(bet);
            console.log('Adding bet: ', response);
            return response;
        } catch (error) {
            console.error('error adding bet to weekly history ', error);
        }
    }

    /**
     * Pulls search results from the datastore and displays them on the html page.
     */
    displaySearchResults() {
        const searchCriteria = this.dataStore.get(SEARCH_CRITERIA_KEY);
        const searchResults = this.dataStore.get(SEARCH_RESULTS_KEY);

        const searchResultsContainer = document.getElementById('search-results-container');
        const searchCriteriaDisplay = document.getElementById('search-criteria-display');
        const searchResultsDisplay = document.getElementById('search-results-display');

        console.log("Search results display: ", searchResultsDisplay);

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;

            console.log("search results 0 : ", searchResults[0]);

            // If searchResults is not null, has at least one record, and the first attribute of the record is an eventId
            if (searchResults && searchResults.length > 0 && !searchResults[0].amountWagered) {
                searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
            } else {
                searchResultsDisplay.innerHTML = this.getHTMLForBetHistory(searchResults);
            }
        }
    }

    /**
     * Create appropriate HTML for displaying searchResults on the page.
     * @param searchResults An array of playlists objects to be displayed on the page.
     * @returns A string of HTML suitable for being dropped on the page.
     */
    getHTMLForSearchResults(searchResults) {
        //const schedules = this.dataStore.get('schedule');
        const events = this.dataStore.get('events');

        if (searchResults.length === 0) {
            return '<h4>No upcoming events found!</h4>';
        }

        let html = '<div class="events-grid">';
        for (const event of searchResults) {

            let options = `
              <input type="text" class="amount-wagered" placeholder="Amount">
              <input type="text" class="odds" placeholder="Odds">
              <input type="text" class="projection" placeholder="O/U, Sprd.">
              <input type="text" class="bookmaker" placeholder="Bookie">
              <label class="checkmark" for="confirm-bet"></label>
            `;

            const homeColor = event.teamHomeColor;
            const awayColor = event.teamAwayColor;
            const homeColorAlt = event.teamHomeColorAlt;
            const awayColorAlt = event.teamAwayColorAlt;

            html += `
            <div class="event-card" style="--home-color:#${homeColor}; --away-color:#${awayColor};">
                <div class="event-data" style="display: none;"
                    data-event-id="${event.eventId}"
                    data-event-name="${event.eventName}"
                    data-event-headline="${event.eventHeadline}"
                    data-event-date="${event.eventDate}"
                    data-event-status="${event.eventStatus}"
                    data-team-home="${event.teamHome}"
                    data-team-away="${event.teamAway}"
                    data-score-home="${event.scoreHome}"
                    data-score-away="${event.scoreAway}"
                    data-team-home-logo="${event.teamHomeLogo}"
                    data-team-away-logo="${event.teamAwayLogo}">
                </div>
                ${event.eventStatusId.includes("2") ? `
                    <div class="live-indicator">
                        <span class="live-circle"></span>
                        LIVE
                    </div>
                ` : ''}
                <div class="event-container">
                    <div class="betting-buttons betting-buttons-away" style="--away-color-alt:#${awayColorAlt}; --away-color:#${awayColor};">
                        <button id="event-${event.teamAway}-moneyline" class="money-line">
                            ML
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-away-${event.teamAway}-moneyline" class="moneyline-dropdown-content">
                                ${options}
                            </div>
                        <button id="event-${event.teamAway}-spread" class="spread">
                            Spread
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-away-${event.teamAway}-spread" class="spread-dropdown-content">
                                ${options}
                            </div>
                        <button id="event-${event.teamAway}-total" class="total">
                            Total
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-away-${event.teamAway}-total" class="total-dropdown-content">
                                ${options}
                            </div>
                    </div>
                    <div class="event-logos" style="--home-color-alt:#${homeColorAlt}; --away-color-alt:#${awayColorAlt}; --home-color:#${homeColor}; --away-color:#${awayColor};">
                        <img src="${event.teamAwayLogo}" class="event-team-logo-away" />
                            <span class="at-symbol">@</span>
                        <img src="${event.teamHomeLogo}" class="event-team-logo-home" />
                    </div>
                    <div class="betting-buttons betting-buttons-home" style="--home-color-alt:#${homeColorAlt}; --home-color:#${homeColor};">
                        <button id="event-${event.teamHome}-moneyline" class="money-line">
                            ML
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-home-${event.teamHome}-moneyline" class="moneyline-dropdown-content">
                                ${options}
                            </div>
                        <button id="event-${event.teamHome}-spread" class="spread">
                            Spread
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-home-${event.teamHome}-spread" class="spread-dropdown-content">
                                ${options}
                            </div>
                        <button id="event-${event.teamHome}-total" class="total">
                            Total
                            <div class="hover-indicator-betting"></div>
                        </button>
                            <div id="betting-dropdown-home-${event.teamHome}-total" class="total-dropdown-content">
                                ${options}
                            </div>
                    </div>
                </div>
                <div class="event-score-status">
                    ${event.eventStatus.includes("EDT") || event.eventStatus.includes("Postponed") ? `
                        <div class="event-status">${event.eventStatus}</div>
                    ` : `
                        <div class="home-score">${event.scoreAway}</div>
                        <div class="event-status">${event.eventStatus}</div>
                        <div class="away-score">${event.scoreHome}</div>
                    `}
                </div>
                <div class="event-details">
                    <div class="event-name">${event.eventName}</div>
                    <div class="event-headline">${event.eventHeadline}</div>
                    <div class="hover-indicator"></div>
                </div>
            </div>`;
        }
        html += '</div>';

        const searchResultsDisplay = document.getElementById('search-results-display');
        searchResultsDisplay.addEventListener('click', (event) => {
            const target = event.target;
            if (target.matches('.money-line, .spread, .total, .projection')) {
                const dropdown = target.nextElementSibling;
                if (dropdown) {
                    dropdown.classList.toggle('show');
                }
            }
            if (target.matches('.checkmark')) {
                this.recordBetDetails(target);
            }
        });

        return html;
    }

    recordBetDetails(target) {

        const eventCard = target.closest('.event-card');

        // Double check there is an event-card present
        if (!eventCard) {
            console.error('Event card not found.');
            return;
        }

        // Retrieve and check the hidden event-data div to retrieve data about event dynamically
        const eventData = eventCard.querySelector('.event-data');
        if (!eventData) {
            console.error('Event data not found.');
            return;
        }

        // Retrieve data for Event from the hidden event-data div
        const eventId = eventData.getAttribute('data-event-id');
        const eventName = eventData.getAttribute('data-event-name');
        const eventStatus = eventData.getAttribute('data-event-status')
        const eventDate = eventData.getAttribute('data-event-date')
        const eventHeadline = eventData.getAttribute('data-event-headline');
        const teamHome = eventData.getAttribute('data-team-home');
        const teamAway = eventData.getAttribute('data-team-away');
        const scoreHome = eventData.getAttribute('data-score-home');
        const scoreAway = eventData.getAttribute('data-score-away');
        const teamHomeLogo = eventData.getAttribute('data-team-home-logo');
        const teamAwayLogo = eventData.getAttribute('data-team-away-logo');

        // Retrieve data from betting-buttons-home or betting-buttons-away depending on the closest (button) to the event click
        const bettingButtons = target.closest('.betting-buttons');

        const isHome = bettingButtons.classList.contains('betting-buttons-home');
        const isAway = bettingButtons.classList.contains('betting-buttons-away');

        const teamBetOn = isHome ? teamHome : teamAway;

        // Retrieve betting market depending on which button was clicked
        let bettingMarket = null;

        if (eventCard.querySelector('.moneyline-dropdown-content.show')) {
            bettingMarket = 'Moneyline';
        } else if (eventCard.querySelector('.spread-dropdown-content.show')) {
            bettingMarket = 'Spread';
        } else if (eventCard.querySelector('.total-dropdown-content.show')) {
            bettingMarket = 'Total';
        }

        console.log(bettingMarket);

        // If the bettingMarket has been set, retrieve values entered by user in dropdown for corresponding dropdown content;
        let amountWagered;
        let odds;
        let projection;
        let bookmakerId;
        if (bettingMarket) {
            const dropdownContentClass = `.${bettingMarket.toLowerCase()}-dropdown-content.show`;
            const dropdownContent = target.closest(dropdownContentClass);

            console.log(dropdownContentClass);

            if (dropdownContent) {
                amountWagered = dropdownContent.querySelector('.amount-wagered').value;
                odds = dropdownContent.querySelector('.odds').value;
                projection = dropdownContent.querySelector('.projection').value;
                bookmakerId = dropdownContent.querySelector('.bookmaker').value;

                // Now you have the amountWagered, odds, and bookmaker values
                console.log({ amountWagered, odds, projection, bookmakerId });
            } else {
                console.error('Dropdown content not found');
            }
        } else {
            console.error('Betting market not determined');
        }

        // If projection is left blank (for a moneyline bet), default to ''
        if (!projection) {
            projection = '';
        }

        const datePlaced = new Date().toISOString();

        const bet = {
            weeklyHistoryId: "-", // Automatically set in back end
            betId: eventId + "-" + bettingMarket + "-" + datePlaced, // Update this as necessary
            userId: "user123", // Update this as necessary
            eventId,
            amountWagered: parseFloat(amountWagered),
            odds: parseFloat(odds),
            teamBetOn,
            projection,
            bettingMarket,
            bookmakerId,
            datePlaced,
            gainOrLoss: 0,
            teamHome,
            scoreHome: parseInt(scoreHome),
            teamHomeLogo,
            teamAway,
            scoreAway: parseInt(scoreAway),
            teamAwayLogo,
            teamWinner: '-',
            scoreTotal: parseInt(scoreHome) + parseInt(scoreAway),
            eventName,
            eventHeadline,
            eventDate,
            eventStatus
        };

        console.log(bet);
        this.addBetToHistory(bet);
    }

    getHTMLForBetHistory(searchResults) {
        const history = this.dataStore.get('weeklyHistoryId');

        if (searchResults.length === 0) {
            return '<h4>No bets placed!</h4>';
        }

        let html = '<table><tr><th></th><th>Event</th><th>Event Date</th><th>Wager</th><th>Odds</th><th>Placed</th><th>Result</th><th>+/-</th><th>Remove</th></tr>';
        for (const bet of searchResults) {
            html += `
            <tr id="${bet.betId}" class="bet-record">
                <td class="bet-logos">
                    <img src="${bet.teamAwayLogo}" class="bet-team-away-logo-large" />
                    <span class="bet-at-symbol">@</span>
                    <img src="${bet.teamHomeLogo}" class="bet-team-home-logo-large" />
                </td>
                <td class="bet-event-details">
                    ${bet.eventName}</br>
                    ${bet.eventHeadline}</br>
                </td>
                <td class="event-date">
                    ${bet.eventDate}
                </td>
                <td class="bet-details">
                    ${bet.amountWagered}</br>
                    ${bet.bettingMarket}</br>
                    ${bet.teamBetOn}
                </td>
                <td class="odds-details">
                    ${bet.odds}</br>
                    ${bet.projection}</br>
                    ${bet.bookmakerId}
                </td>
                <td class="bet-date-placed">
                    ${bet.datePlaced}
                </td>
                <td class="bet-result">
                        <img src="${bet.teamAwayLogo}" class="bet-team-away-logo-small" />
                            ${bet.teamWinner.includes("home") ? `<span class="bet-winner-symbol"> > </span>` :
                            bet.teamWinner.includes("away") ? `<span class="bet-winner-symbol"> > </span>` :
                            `<span class="bet-winner-symbol">TBD</span>`}
                        <img src="${bet.teamHomeLogo}" class="bet-team-home-logo-small" />
                </td>
                <td class="bet-gain-or-loss">
                    ${bet.gainOrLoss}
                </td>
                <td class="bet-remove-button">
                    <button id="${bet.betId}" class="button remove-bet">Remove Bet</button>
                </td>
            </tr>`;
        }
        html += '</table>';

        return html;
    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const getEvents = new GetEvents();
    getEvents.mount();
};

window.addEventListener('DOMContentLoaded', main);
