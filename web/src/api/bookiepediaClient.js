import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class BookiepediaClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getSchedule', 'fetchSchedule', 'getEventsForSchedule', 'addBetToHistory', 'getBetsForHistory', 'removeBetFromHistory'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Search for a soong.
     * @param criteria A string containing search criteria to pass to the API.
     * @returns The playlists that match the search criteria.
     */
    async getSchedule(leagueId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`league/${leagueId}/schedule`);
            console.log('API Response: ', response.data);
            return response.data.schedule?.scheduleId;
        } catch (error) {
            this.handleError(error, errorCallback);
        }

    }

    /**
    * Pings the ESPN API to refresh Events for NBA, NHL, and MLB
    * Creates/updates Schedule objects
    * Schedules are a collection of events for a League on a given day
    */
    async fetchSchedule(errorCallback) {
        try {
            const response = await this.axiosClient.post(`/schedule`);
            console.log('API Response: ', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Currently retrieves the first 20 events of a schedule, ordered by the date/time of the event
    * Schedules are able to have a maximum of 100 events; these are broken up into chunks of 20 events in espnDAO
    * Will eventually include pagination for other 5 chunks
    */
    async getEventsForSchedule(scheduleId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/schedule/${scheduleId}/events`);
            console.log('API Response: ', response.data);
            return response.data.eventList;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Returns a user's betting history for the current week
    * Will eventually include pagination for previous weeks, or divide them by weeklyHistoryId in the same view
    * weeklyHistoryId ex -> WH-2024-06-4
    */
    async getBetsForHistory(weeklyHistoryId, errorCallback) {
        try {
            const response = await this.axiosClient.get(`/history/${weeklyHistoryId}/bets`);
            console.log('API Response: ', response.data);
            return response.data.betList;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Adds a bet to a users betting history
    * weeklyHistoryId is automatically set to "WH" + the current year/month/week# to be added to a WeeklyHistory's bet list
    * Triggered after entering values in betting button's dropdown window and clicking the checkmark
    */
    async addBetToHistory(bet, errorCallback) {
        try {
             const response = await this.axiosClient.put('/history/bets', bet, {
                headers: {
                        'Content-Type': 'application/json'
                }
            });
            console.log('API Response: ', response.data);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
    * Removes a bet from a user's WeeklyHistory
    * Triggered after clicking red X in betting history
    */
    async removeBetFromHistory(weeklyHistoryId, bet, errorCallback) {
        try {
            const response = await this.axiosClient.delete(`/history/${weeklyHistoryId}/bets/${bet}`);
            console.log('API Response: ', response.data);
            await this.getBetsForHistory(weeklyHistoryId);
            return response.data;
        } catch (error) {
            this.handleError(error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
