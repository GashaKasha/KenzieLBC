package com.kenzie.appserver.service.model;

import java.util.ArrayList;
import java.util.List;

public class Collection {

    private final String id;
    private final String collectionName;
    private final String type;
    private final String description;
    private final List<String> collectionItemNames;
    private final String creationDate;

    public Collection(String id, String collectionName, String type, String description, String creationDate, List<String> collectionItemNames) {
        this.id = id;
        this.creationDate = creationDate;
        this.collectionName = collectionName;
        this.type = type;
        this.description = description;
        this.collectionItemNames = new ArrayList<>();
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
