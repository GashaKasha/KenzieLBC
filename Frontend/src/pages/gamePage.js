import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import GamePageClient from "../api/gamePageClient";
import CollectionClient from "../api/collectionPageClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class GamePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods([/*'onGet',*/ 'onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
        console.log(this.dataStore);
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
//        document.getElementById('get-by-id-form').addEventListener('submit', this.onGet);
        document.getElementById('create-game-form').addEventListener('submit', this.onCreate);
        this.client = new GamePageClient();
        this.collectionClient = new CollectionClient();
        let result = await this.collectionClient.getAllCollections(this.errorHandler);
        console.log(result)
        this.dataStore.set("allCollections", result);
        console.log("the collections are" + result);
        this.renderExample();
        this.dataStore.addChangeListener(this.renderExample)
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("collections-display");

        resultArea.innerHTML = ""

        const allCollections = this.dataStore.get("allCollections");

        const toArray = Object.entries(allCollections);
        console.log(toArray);

        if (allCollections) {
            const ul = document.createElement("ul");
            for (let i = 0; i < allCollections.length; i++) {
                const li = document.createElement("li");
                console.log("inside the for loop " + allCollections[i]);
                li.innerHTML = `
                    ${allCollections[i].collectionName} ............. ${allCollections[i].collectionId}
                `;
                ul.append(li);
            }
            resultArea.append(ul);
        }else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

////Todo
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
            this.showMessage(`Created ${name}!`);
        } else {
            this.errorHandler("Error adding a board game!  Try again...");
        }

        document.getElementById("game-name").value = '';
        document.getElementById("collection-id").value = '';
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