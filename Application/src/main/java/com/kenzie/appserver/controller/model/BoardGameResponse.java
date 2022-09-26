package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class BoardGameResponse {
    
    @JsonProperty("Id")
    private String id;
    
    @JsonProperty("Name")
    private String name;
    
    @JsonProperty("NumberOfPlayers")
    private String numberOfPlayers;

    @JsonProperty("YearPublished")
    private String yearPublished;

    @JsonProperty("AveragePlayTime")
    private String averagePlayTime;

    @JsonProperty("CollectionId")
    private String collectionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(String yearPublished) {
        this.yearPublished = yearPublished;
    }

    public String getAveragePlayTime() {
        return averagePlayTime;
    }

    public void setAveragePlayTime(String averagePlayTime) {
        this.averagePlayTime = averagePlayTime;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
