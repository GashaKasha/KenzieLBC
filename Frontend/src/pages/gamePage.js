import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import GamePageClient from "../api/gamePageClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class GamePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([/*'onGet',*/ 'onCreate'/*, 'renderExample'*/], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        /*document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);*/
        document.getElementById('create-game-form').addEventListener('submit', this.onCreate);
        this.client = new GamePageClient();

        /*this.dataStore.addChangeListener(this.renderExample)*/
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    /*async renderExample() {
        let resultArea = document.getElementById("result-info");

        const example = this.dataStore.get("example");

        if (example) {
            resultArea.innerHTML = `
                <div>ID: ${example.id}</div>
                <div>Name: ${example.name}</div>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }*/

    // Event Handlers --------------------------------------------------------------------------------------------------

//    async onGet(event) {
//        // Prevent the page from refreshing on form submit
//        event.preventDefault();
//
//        let id = document.getElementById("id-field").value;
//        this.dataStore.set("example", null);
//
//        let result = await this.client.getExample(id, this.errorHandler);
//        this.dataStore.set("example", result);
//        if (result) {
//            this.showMessage(`Got ${result.name}!`)
//        } else {
//            this.errorHandler("Error doing GET!  Try again...");
//        }
//    }

    async onCreate(event) {
        console.log("entering create");
        // Prevent the page from refreshing on form submit
        event.preventDefault();
        /*this.dataStore.set("example", null);*/

        let name = document.getElementById("game-name").value;
        console.log(name);
        let numPlayers = document.getElementById("number-of-players").value;
        console.log(numPlayers);
        let yearPublished = document.getElementById("year-published").value;
        console.log(yearPublished);
        let avgPlayTime = document.getElementById("average-playtime").value;
        console.log(avgPlayTime);
        let collectionId = document.getElementById("collection-id").value;
        console.log(collectionId);

        const createdBoardGame = await this.client.addBoardGame(name, numPlayers, yearPublished, avgPlayTime, collectionId, this.errorHandler);

        if (createdBoardGame) {
            this.showMessage(`Created ${createdBoardGame.name}!`);
        } else {
            this.errorHandler("Error adding a board game!  Try again...");
        }

        this.dataStore.set("boardGame", createdBoardGame);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const gamePage = new GamePage();
    gamePage.mount();
};

window.addEventListener('DOMContentLoaded', main);