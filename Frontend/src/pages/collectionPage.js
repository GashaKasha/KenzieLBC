import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CollectionClient from "../api/collectionPageClient";

var CURRENT_STATE;

/**
 * Logic needed for the view playlist page of the website.
 */
class CollectionPage extends BaseClass {
    constructor() {
        super();
        this.bindClassMethods(['onCreateCollection', 'onGetCollection', 'onDeleteCollection', 'confirmDeleteCollection', 'renderCollection'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        // document.getElementById
        document.getElementById('create-collection-form').addEventListener('submit', this.onCreateCollection);
        document.getElementById('search-collection').addEventListener('submit', this.onGetCollection);
        // TODO: Add listeners for form-delete-btn + add-items btn

        this.client = new CollectionClient();

        this.dataStore.addChangeListener(this.renderCollection);
        //this.renderCollection();
    }

    // Render Methods --------------------------------------------------------------------------------------------------
    async renderCollection() {
        console.log("Entering render method...");
        let getState = this.dataStore.get(CURRENT_STATE);


        if (!(getState)) {
            console.log("ERROR: Unable to retrieve current state!");
        }

        if (getState === 'CREATE') {
            // Do create things
            console.log("State = CREATE");
            let resultArea = document.getElementById('collection-result-info');

            const collection = this.dataStore.get("collection");
            console.log(collection);
            console.log(collection.collectionId);

            if (collection) {
                document.getElementById("create-collection-results").style.display = "flex";
                resultArea.innerHTML = `
            <div>${collection.collectionId}</div>
            `
            } else {
                resultArea.innerHTML = "Error Creating Collection! Try Again... ";
            }
        } else if (getState === 'GET') {
            // Do get things
            // retrieve values
            console.log("State = GET");
            const getCollection = this.dataStore.get("getCollection");

            if (getCollection) {
                console.log(getCollection);
                // Save in dataStore or localStorage
                localStorage.setItem("collectionId", getCollection.collectionId);
                let collectionId = getCollection.collectionId;
                let collectionDate = getCollection.creationDate;
                let collectionName = getCollection.collectionName;
                // Save in dataStore or localstorage
                localStorage.setItem("collectionType", getCollection.type);
                let collectionType = getCollection.type;
                let collectionDesc = getCollection.description;
                let collectionItems = getCollection.collectionItemNames;

                await this.generateTable(collectionId,
                    collectionDate,
                    collectionName,
                    collectionType,
                    collectionDesc,
                    collectionItems);

                document.getElementById('table-delete-btn').addEventListener('click', this.onDeleteCollection);
            } else {
                this.errorHandler("Error Getting Collection! Try Again... ");
                console.log("Error Getting Collection!");
            }

        } else if (getState === 'DELETE') {
            console.log("State = 'DELETE'");
            const deleteCollectionId = this.dataStore.get("deleteCollectionId");

            if (deleteCollectionId) {
                console.log(deleteCollectionId);
                this.showMessage(`Collection: ${deleteCollectionId} has been deleted!`);
            } else {
                this.errorHandler(`Error Deleting Collection ID: ${deleteCollectionId}`);
                console.log("Error Deleting Collection ID...");
            }
        } else {
            console.log("ERROR: Unable to process current state!");
        }
        // } else if (getState === 'DELETE') {
        //     // Do delete things
        // } else {
        //}
    }

    // } else {
    //     console.log("ERROR: Unable to retrieve state!");
    // }

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
        // CURRENT_STATE = "create";
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
            this.showMessage('Collection Created!');
        } else {
            this.errorHandler("Error creating collection! Try again...");
            console.log("POST isn't working...");
        }

        // Clear form data - form.clear
        // setState with multiple
        this.dataStore.setState({
            [CURRENT_STATE]: "CREATE",
            ["collection"]: createCollection
        });
        // this.dataStore.set("collection", createCollection);
        // this.dataStore.set("CURRENT_STATE", CURRENT_STATE);
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
        CURRENT_STATE = "get";

        // // Prevent the page from refreshing on form submit
        event.preventDefault();

        let collectionId = document.getElementById('search-input').value;
        console.log(collectionId);
        if (collectionId === '' || collectionId.trim().length === 0) {
            this.errorHandler("ERROR: Must enter valid Collection ID!");
            console.log("Collection ID: " + collectionId + " is empty");
        }

        //localStorage.setItem("collectionId", collectionId);
        try {
            const getCollection = await this.client.getCollectionById(collectionId, this.errorHandler);

            if (getCollection) {
                //         // TODO: What format should the collection return; table?
                //         // TODO: If 'collectionItemNames' is empty, result empty
                this.showMessage(`Found Collection: ${collectionId}`);
                this.dataStore.setState({
                    [CURRENT_STATE]: "GET",
                    ["getCollection"]: getCollection
                });
            } else {
                this.errorHandler("Error retrieving collection by ID: " + `${collectionId}` + "Try again with a valid collection ID");
                console.log("GET isn't working...");
            }
        } catch(e) {
            console.log(e);
        }
    }


    // Delete collection for a given ID
    async onDeleteCollection(event) {
        console.log("Entering onDeleteCollection method...");
        event.preventDefault();

        // TODO: Retrieve the collectionId
        let collectionId = document.getElementById('search-input').value;

        // TODO: Validate the collectionId
        if (collectionId === '' || collectionId.trim().length === 0) {
            this.errorHandler("ERROR: Must enter valid Collection ID!");
            console.log("Collection ID is empty" + " " + collectionId);
        }

        // TODO: If CollectionId, save to dataStore
        if (collectionId) {
            await this.confirmDeleteCollection(collectionId);
        } else {
            this.errorHandler("Error: Collection ID: " + `${collectionId}` + " Invalid!");
            console.log("Issue with Collection ID on DELETE...");
        }
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
        // CURRENT_STATE = "delete";

        event.preventDefault();

        // TODO: Retrieve the collectionId

        // TODO: Validate the collectionId

        // TODO: If CollectionId, save to dataStore

        // TODO: Call the delete confirmation by id method - to be created
        // this.dataStore.set("CURRENT_STATE", CURRENT_STATE);
    }

    async confirmDeleteCollection(collectionId) {
        // TODO: Add html or js for button confirmation
        console.log("Entering the confirmDeleteCollection method...");
        // event.preventDefault();

        var msg = prompt("Are you sure you want to delete this collection? Enter 'yes' or 'no'");
        var response = msg.toLowerCase();

        if (response === null || response === "") {
            this.errorHandler("ERROR: Must enter either yes or no!");
        }

        let deleteCollection;

        if (response === 'yes') {
            // do something - make api call
            // TODO: retrieve collectionId from dataStore
            let collectionId = this.dataStore.get("collection");
            // TODO: Does this need to be saved in a variable?
            deleteCollection = await this.client.deleteCollectionById(collectionId, this.errorHandler);
            // returnMsg about successful delete (showMessage())?
            this.showMessage("Collection Deleted!");
        } else if (response === 'no') {
            // do something - can request; exit
            this.showMessage("Collection Not Deleted!");
        } else {
            this.errorHandler("ERROR: Must enter either yes or no!");
            // Or a different returnMsg?
        }

        this.dataStore.setState({
            [CURRENT_STATE]: "DELETE",
            ["deleteCollection"]: deleteCollection,
            ["deleteCollectionId"]: collectionId
        });

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

    async generateTable(id, date, name, type, description, itemNames) {
        // TODO: How to confirm the correct collections are being retrieved from the dataStore?
        // TODO: Get the 'collection' from the DataStore & extract values that are needed
        // Create scaffolding for table in
        // html file first
        console.log("Entering generateTable method...");
        // event.preventDefault();

        if (itemNames.size === 0) {
            itemNames = "null";
        }

        // Get reference for the body
        //var tableDiv = document.getElementById('');

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
            "Collection ID",
            "Collection Creation Date",
            "Collection Name",
            "Collection Type",
            "Collection Description",
            "Collection Item Names",
            "Delete Collection",
            "Add Items To Collection",
            "Close Table"
        ];

        // let row = table.insertRow(-1);
        for (var i = 0; i < headerRowNames.length; i++) {
            // Create column element
            var th = document.createElement("th");
            // Create cell element
            var text = document.createTextNode(headerRowNames[i]);
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
        var trData = document.createElement("tr");

        var td1 = document.createElement("td");
        var td2 = document.createElement("td");
        var td3 = document.createElement("td");
        var td4 = document.createElement("td");
        var td5 = document.createElement("td");
        var td6 = document.createElement("td");
        var td7 = document.createElement("td");
        var td8 = document.createElement("td");
        var td9 = document.createElement("td");


        // The below variables are to be populated with
        // the results from the API call to the
        // getCollectionById endpoint + buttons
        var colId = document.createTextNode(id);
        var colCreationDate = document.createTextNode(date);
        var colName = document.createTextNode(name);
        var colType = document.createTextNode(type);
        var colDesc = document.createTextNode(description);
        var colItemNames = document.createTextNode(itemNames);

        td1.appendChild(colId);
        td2.appendChild(colCreationDate);
        td3.appendChild(colName);
        td4.appendChild(colType);
        td5.appendChild(colDesc);
        td6.appendChild(colItemNames);
        td7.innerHTML = "<button type='button' id='table-delete-btn'>DELETE</button>";
        td8.innerHTML = "<button type='button' id='table-add-items-btn'>Add To Collection</button>";
        td9.innerHTML = "<input type='button' id='close-table' value='CLOSE' onclick=document.getElementById(\"get-collection-table\").style.display='none'>";

        trData.appendChild(td1);
        trData.appendChild(td2);
        trData.appendChild(td3);
        trData.appendChild(td4);
        trData.appendChild(td5);
        trData.appendChild(td6);
        trData.appendChild(td7);
        trData.appendChild(td8);
        trData.appendChild(td9);

        table.appendChild(trData);

        // The last two cells should be populated with buttons

        // TODO: Before exiting loop, append the variables to the td
        // i.e. td1.appendChild(colId);
        // And append td to tr
        // i.e. trData.appendChild(td1);
        // Finally append tr to the table element
        // table.appendChild(tr);
        // TODO: append table to document body
        // i.e. document.body.appendChild(table);
        // TODO: Method to handle page direction
        // on click of 'addToCollection'
        // retrieve collectionType from localStorage
        // depending on collectionType, redirect to correct page

        document.body.appendChild(table);
        // table.setAttribute("border-collapse", "collapse");
        // td.setAttribute("border", "1px solid #cecfd5");
        // td.setAttribute("padding", "10px 15px");
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