package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionGetResponse {

    @JsonProperty("collectionId")
    private String collectionId;

    @JsonProperty("creationDate")
    private String creationDate;

    @JsonProperty("collectionName")
    private String collectionName;

    @JsonProperty("type")
    private String type;

    @JsonProperty("description")
    private String description;

    @JsonProperty("collectionItemNames")
    private List<String> collectionItemNames;

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCollectionItemNames(List<String> collectionItemNames) {
        this.collectionItemNames = collectionItemNames;
    }
}
