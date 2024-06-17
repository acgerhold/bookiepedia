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

        this.bindClassMethods(['mount', 'displaySearchResults', 'getHTMLForSearchResults', 'getSchedule', 'fetchSchedule', 'getEventsForSchedule'], this);

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
            [SEARCH_CRITERIA_KEY]: '',
            [SEARCH_CRITERIA_KEY]: response,
            });
        } catch (error) {
            console.error('error fetching schedule', error);
        }
    }

    async getEventsForSchedule(results) {
        console.log('Retrieving events...');
        try {
            const response = await this.client.getEventsForSchedule(results);
            console.log('Events: ', response)
            return response;
        } catch (error) {
            console.error('error retrieving events', error);
            return [];
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

        if (searchCriteria === '') {
            searchResultsContainer.classList.add('hidden');
            searchCriteriaDisplay.innerHTML = '';
            searchResultsDisplay.innerHTML = '';
        } else {
            searchResultsContainer.classList.remove('hidden');
            searchCriteriaDisplay.innerHTML = `"${searchCriteria}"`;
            searchResultsDisplay.innerHTML = this.getHTMLForSearchResults(searchResults);
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
            return '<h4>No results found</h4>';
        }

        let html = '<div class="events-grid">';
        for (const event of searchResults) {

            let options = `
              <input type="text" id="amount-wagered" placeholder="Amount">
              <input type="text" id="odds" placeholder="Odds">
              <input type="text" id="bookmaker" placeholder="Bookie">
            `;

            const homeColor = event.teamHomeColor;
            const awayColor = event.teamAwayColor;
            const homeColorAlt = event.teamHomeColorAlt;
            const awayColorAlt = event.teamAwayColorAlt;

            html += `
            <div class="event-card" style="--home-color:#${homeColor}; --away-color:#${awayColor};">
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
            if (target.matches('.money-line, .spread, .total')) {
                const dropdown = target.nextElementSibling;
                if (dropdown) {
                    dropdown.classList.toggle('show');
                }
            }
        });

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
