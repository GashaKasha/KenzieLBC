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
        this.bindClassMethods(['onCreateCollection', 'onGetCollection', 'renderCollection'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        // document.getElementById
        document.getElementById('create-collection-form').addEventListener('submit', this.onCreateCollection);
        // TODO: should this be 'onClick' or on 'submit'?
        document.getElementById('search-input').addEventListener('submit', this.onGetCollection);
        this.client = new CollectionClient();

        this.dataStore.addChangeListener(this.renderCollection)
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    // TODO: May be easier to just have multiple renderMethods?
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
    async onGetCollection(event) {

        /* Add to place where it's needed
        *   item.style.setProperty('--display', 'none');
        * */
        // TODO: Add some sort of id validation?
        // TODO: Question about saving objects to the DataStore
        // TODO: When objects are saved to the DataStore or LocalStorage...
        // TODO: Question - Does the API call response hit this method?
        console.log("Entering onGetCollection method...");
        endpoint = "getCollection";

    // // Prevent the page from refreshing on form submit
        event.preventDefault();

        let collectionId = document.getElementById('search-input').value;
        if (collectionId === '' || collectionId.trim().length === 0) {
            this.errorHandler("ERROR: Must enter valid Collection ID!");
            console.log("Collection ID is empty" + " " + collectionId);
        }

        localStorage.setItem("collectionId", collectionId);
        let getCollection = await this.client.getCollectionById(collectionId, this.errorHandler);

        if (getCollection) {
    //         // TODO: What format should the collection return; table?
    //         // TODO: If 'collectionItemNames' is empty, result empty
            this.showMessage(`Collection:
                ${getCollection.name}
                ${getCollection.type}
                ${getCollection.description}
            `)
        } else {
            this.errorHandler("Error retrieving collection by ID: " + ${collectionID} + "Try again with a valid collection ID");
        }
        this.dataStore.set("collection", getCollection);
    }



    // Delete collection for a given ID
    async onDeleteCollection(event) {
        console.log("Entering onDeleteCollection method...");
        endpoint = "deleteCollection";

        event.preventDefault();

        // TODO: Retrieve the collectionId

        // TODO: Validate the collectionId

        // TODO: If CollectionId, save to dataStore

        // TODO: Call the delete confirmation method

    }

    // TODO: Is another delete method needed to handle 'Delete Collection' button?
    // TODO: CollectionPage 'Delete Collection' could use window.prompt()

    async onCollectionPageDelete(event) {
        // TODO: Workflow
        // 1. When user clicks the 'DeleteCollection' button, this method is called
        // 2. Prompt user to enter a collection id
        // 3. Validate collection id entered
        // 4. If collection Id valid, save the collection id to the dataStore
        // 5. Call the delete confirmation method
        console.log("Entering onCollectionPageDelete method...");
        endpoint = "deleteCollection";

        event.preventDefault();

        // TODO: Retrieve the collectionId

        // TODO: Validate the collectionId

        // TODO: If CollectionId, save to dataStore

        // TODO: Call the delete confirmation by id method - to be created
    }

    async confirmDeleteCollection() {
        // TODO: Add html or js for button confirmation
        console.log("Entering the confirmDeleteCollection method...");

        var msg = prompt("Are you sure you want to delete this collection? Enter 'yes' or 'no'");
        var response = msg.toLowerCase();
        var returnMsg;

        if (response === null || response === "") {
            this.errorHandler("ERROR: Must enter either yes or no!");
        }



        if (response === 'yes') {
            // do something - make api call
            // TODO: retrieve collectionId from dataStore
            let collectionId = this.dataStore.get("collection");
            // TODO: Does this need to be saved in a variable?
            await this.client.getCollectionById(collectionId, this.errorHandler);
            // returnMsg about successful delete (showMessage())?
        } else if (response === 'no') {
            // do something - can request; exit
            returnMsg = "Collection Not Deleted!";
        } else {
            this.errorHandler("ERROR: Must enter either yes or no!");
            // Or a different returnMsg?
        }


        // Alternative method
        // Try with prompt first
        // if (confirm("Are you sure you want to delete this collection? Select 'OK' to confirm deletion.")) {
        //     Make API Call
        //     returnMsg = "Collection Deleted"
        // } else {
        //      returnMsg = "Collection Not Deleted!"
        // }
    }

    // Do popup the 'easy' way
    // async popupForm(form, windowname) {
    //     if (! window.focus) return true;
    //     window.open('', windowname, 'height=200, width=400, scrollbars=yes');
    //     form.target=windowname;
    //     return true;
    // }

    async generateTable() {
        // TODO: How to confirm the correct collections are being retrieved from the dataStore?
        // TODO: Get the 'collection' from the DataStore & extract values that are needed

        // Create scaffolding for table in
        // html file first

        console.log("Entering generateTable method...");

        // Get reference for the body
        var tableDiv = document.getElementById('');

        // Create a table element
        var table = document.createElement("table");

        // Set table id
        table.setAttribute('id', 'get-collection-table');
        var tr = document.createElement("tr");

        // Define column count
        let columnCount = 8;
        let rowCount = 2;

        // Add the header row
        const headerRowNames = [
            "Colllection ID",
            "Collection Creation Date",
            "Collection Name",
            "Collection Type",
            "Collection Description",
            "Collection Item Names",
            "Delete Collection",
            "Add Items To Collection"
        ];

        let row = table.insertRow(-1);
        for (var i = 0; i < headerRowNames.length; i++) {
            // Create column element
            var th = document.createElement("th");
            // Create cell element
            var text = document.createTextNode(columnCount[i]);
            // headerCell.innerHTML = headerRowNames[i];
            th.appendChild(text);
            tr.appendChild(th);
            // row.appendChild(headerCell);
        }
        table.appendChild(tr);

        // TODO: Populate table with data (separate method?)
        // Does this even need to be in a for loop,
        // Data will only populate a single row, the header will populate the other
        // Loop through the data returned and add
        for (var j = 0; j < rowCount; j++) {
            var trData = document.createElement("tr");

            var td1 = document.createElement("td");
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            var td5 = document.createElement("td");
            var td6 = document.createElement("td");
            var td7 = document.createElement("td");
            var td8 = document.createElement("td");

            // The below variables are to be populated with
            // the results from the API call to the
            // getCollectionById endpoint + buttons
            var colId = document.createTextNode('');
            var colCreationDate = document.createTextNode('');
            var colName = document.createTextNode('');
            var colType = document.createTextNode('');
            var colDesc = document.createTextNode('');
            var colItemNames = document.createTextNode('');
            var deleteCol;
            deleteCol.innerHTML = "<button type='button' id='delete-collection-table'</button>";
            var addToCollection;
            addToCollection = "<button type='button' id='add-to-collection-table'</button>";

            // The last two cells should be populated with buttons

            // TODO: Before exiting loop, append the variables to the td
            // i.e. td1.appendChild(colId);
            // And append td to tr
            // i.e. trData.appendChild(td1);
            // Finally append tr to the table element
            // table.appendChild(tr);
        }
        // TODO: append table to document body
        // i.e. document.body.appendChild(table);
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