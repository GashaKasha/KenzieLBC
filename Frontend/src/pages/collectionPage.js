import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CollectionClient from "../api/collectionPageClient";

var endpoint;

/**
 * Logic needed for the view playlist page of the website.
 */
class CollectionPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreateCollection', 'renderCollection'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        // document.getElementById
        document.getElementById('create-collection-form').addEventListener('submit', this.onCreateCollection);
        // document.getElementById('collection-search').addEventListener('click', this.onGetCollection);
        this.client = new CollectionClient();

        this.dataStore.addChangeListener(this.renderCollection)
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    async renderCollection() {
        console.log("Entering render method...");
        let resultArea = document.getElementById('collection-result-info');

        const collection = this.dataStore.get("collection");
        console.log(collection);

        console.log("Inside the render method...");
        console.log(collection.collectionId);

        if (collection) {
            resultArea.innerHTML = `
                <div>Collection ID: ${collection.collectionId}</div>
            `
        } else {
            resultArea.innerHTML = "Error Creating Collection! Try Again... ";
        }
    }

    // TODO: Need to figure out why this isn't working - needed for different actions
    // Add conditional to distinguish between the different endpoints
    // if (endpoint === 'createCollection') {
    //     // Render collection id in a popup window
    //     // with a copy id button
    //     if (collection) {
    //         resultArea.innerHTML = `
    //         <div>Collection ID: ${collection.collectionId}</div>
    //     `
    //     } else {
    //         resultArea.innerHTML = "Error Creating Collection! Try Again... ";
    //     }
    // } else if (endpoint === 'deleteCollection') {
    //     // Enter a collectionId, click 'Delete'
    //     // onClick, render a confirmation window
    //     // if yes clicked, confirm deletion
    //     // if no clicked, exit
    //
    //
    // } else if (endpoint === 'getCollection') {
    //     // Enter a collectionId in search bar
    //     // onClick, render a table with options
    //     // 'Delete Collection' button - onClick
    //     // renders a confirmation window similar to above
    //     // 'Add Items to Collection' button
    //     // If collection == card game, redirect to
    //     // card game page, with collectionId passed as well
    //     // if collection == board game, same behavior
    // }
    //
    // }



    // When render off search, generate html with buttons and bind to methods that does endpoint requests
    // Or use render to redirect to a new page
    // declare a global variable that has that id in it
    // all JS files will have access to that variable, so could create another global var
    // here for the collectionId
    // get global working and then focus on passing the collectionId automatically later

    // Event Handlers --------------------------------------------------------------------------------------------------
    // Create a new collection
    async onCreateCollection(event) {
        console.log("Entering onCreateCollection method...");
        event.preventDefault();
        endpoint = "createCollection";
        let collectionName = document.getElementById('collection-name').value;
        let collectionType = document.getElementById('collection-type').value;
        let collectionDescription = document.getElementById('collection-description').value;

        const createCollection = await this.client.createCollection(
            collectionName,
            collectionType,
            collectionDescription,
            this.errorHandler
        );

        // this.dataStore.set("collection", createCollection);

        if (createCollection) {
            this.showMessage('Collection Created!')
        } else {
            this.errorHandler("Error creating collection! Try again...");
            console.log("POST isn't working...");
        }
        this.dataStore.set("collection", createCollection);
    }

    // Get collection for a given ID
    // async onGetCollection(event) {
    //
    //     /* Add to place where it's needed
    //     *   item.style.setProperty('--display', 'none');
    //     * */
    //     console.log("Entering onGetCollection method...");
    //     endpoint = "getCollection";
    //
    // // Prevent the page from refreshing on form submit
    //     event.preventDefault();
    //
    //     let collectionId = document.getElementById('search-input').value;
    //     localStorage.setItem("collectionId", collectionId);
    //     let getCollection = await this.client.getCollectionById(collectionId, this.errorHandler);
    //
    //     if (getCollection) {
    //         // TODO: What format should the collection return; table?
    //         // TODO: If 'collectionItemNames' is empty, result empty
    //         this.showMessage(`Collection:
    //             ${getCollection.name}
    //             ${getCollection.type}
    //             ${getCollection.description}
    //         `)
    //     } else {
    //         this.errorHandler("Error retrieving collection by ID: " + ${result.name} + "Try again with a valid collection ID");
    //     }
    //     this.dataStore.set("collectionId", getCollection);
    // }



    // Delete collection for a given ID
    async onDeleteCollection(event) {
        //     endpoint = "deleteCollection";

    }

    async popupForm(form, windowname) {
        if (! window.focus) return true;
        window.open('', windowname, 'height=200, width=400, scrollbars=yes');
        form.target=windowname;
        return true;
    }

    async createGetResponseTable() {
        // Create scaffolding for table in
        // html file first

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const collectionPage = new CollectionPage();
    collectionPage.mount();
};

window.addEventListener('DOMContentLoaded', main);