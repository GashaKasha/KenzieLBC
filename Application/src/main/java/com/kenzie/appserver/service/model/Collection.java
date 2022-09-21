package com.kenzie.appserver.service.model;

import java.util.List;

public class Collection {

    private final String id;
    private final String collectionName;
    private final String type;
    private final String description;
    private final List<String> collectionItemNames;
    private final String creationDate;

    public Collection(String id, String collectionName, String type, String description, List<String> collectionItemNames, String creationDate) {
        this.id = id;
        this.collectionName = collectionName;
        this.type = type;
        this.description = description;
        this.collectionItemNames = collectionItemNames;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
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

    public String getCreationDate() {
        return creationDate;
    }
}
