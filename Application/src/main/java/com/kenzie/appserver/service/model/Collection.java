package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private final String id;
    private final String creationDate;
    private final String collectionName;
    private final String type;
    private final String description;
    private final List<String> collectionItemNames;

    public Collection(String id, String creationDate, String collectionName, String type, String description,  List<String> collectionItemNames) {
        this.id = id;
        this.creationDate = creationDate;
        this.collectionName = collectionName;
        this.type = type;
        this.description = description;
        this.collectionItemNames = new ArrayList<>(collectionItemNames);
    }

    public String getId() {
        return id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCollectionItemNames() {
        return collectionItemNames;
    }
}
