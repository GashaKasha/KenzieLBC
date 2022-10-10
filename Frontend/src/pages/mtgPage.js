import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import MTGPageClient from "../api/mtgPageClient";

/**
 * Logic needed for the view playlist page of the website.
 */
class MTGPage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('add-card-form').addEventListener('submit', this.onCreate);
        this.client = new MTGPageClient();

        this.dataStore.addChangeListener(this.renderExample);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

   async renderExample() {
        let resultArea = document.getElementById('mtg-card-info');

        const addCard = this.dataStore.get("mtgCard");
        console.log(addCard);

        if (addCard) {
            console.log("Entering if statement...")
            document.getElementById("mtg-card-name").style.display = "flex";
            resultArea.innerHTML = `
              <div>${addCard.collectionName}</div>
              `
        } else {
            resultArea.innerHTML = "Error adding Card! Try Again... ";
        }
    }

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

        let name = document.getElementById("card-name").value;
        let releasedSet = document.getElementById("released-set").value;
        let cardType = document.getElementById("card-type").value;
        let manaCost = document.getElementById("mana-cost").value;
        let powerToughness = document.getElementById("power-and-toughness").value;
        let cardAbilities = document.getElementById("card-abilities").value;
        let numOfCardsOwned = document.getElementById("number-of-cards-owned").value;
        let artist = document.getElementById("artist").value;
        let collectionId = document.getElementById("collection-id").value;

        const createdMTGCard = await this.client.addMTGCard(name, releasedSet, cardType, manaCost, powerToughness, cardAbilities, numOfCardsOwned, artist, collectionId, this.errorHandler);

        if (createdMTGCard) {
            this.showMessage(`Created ${name}!`);
        } else {
            this.errorHandler("Error adding a card!  Try again...");
        }

        this.dataStore.set("mtgCard", createdMTGCard);
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const mtgPage = new MTGPage();
    mtgPage.mount();
};

window.addEventListener('DOMContentLoaded', main);