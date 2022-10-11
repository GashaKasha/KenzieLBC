import BaseClass from "../util/baseClass";
import axios from 'axios'

export default class MTGPageClient extends BaseClass {

    constructor(props = {}){
        super();
        const methodsToBind = ['clientLoaded', 'addMTGCard'];
        this.bindClassMethods(methodsToBind, this);
        this.props = props;
        this.clientLoaded(axios);
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     * @param client The client that has been successfully loaded.
     */
    clientLoaded(client) {
        this.client = client;
        if (this.props.hasOwnProperty("onReady")){
            this.props.onReady();
        }
    }


    async addMTGCard(name, releasedSet, cardType, manaCost, powerToughness, cardAbilities, numOfCardsOwned, artist, collectionId, errorCallback = console.error) {
        console.log("Entering addMTGCard in Client.")
        try {
            const response = await this.client.post(`/cards/mtg`, {
                "name": name,
                "releasedSet": releasedSet,
                "cardType": cardType,
                "manaCost": manaCost,
                "powerToughness": powerToughness,
                "cardAbilities": cardAbilities,
                "numberOfCardsOwned": numOfCardsOwned,
                "artist": artist,
                "collectionId": collectionId
            });
            return response.data;
        } catch (error) {
            this.handleError("addMTGCard", error, errorCallback);
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(method, error, errorCallback) {
        console.error(method + " failed - " + error);
        if (error.response.data.message !== undefined) {
            console.error(error.response.data.message);
        }
        if (errorCallback) {
            errorCallback(method + " failed - " + error, error);
        }
    }
}