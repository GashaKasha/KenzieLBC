package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class BoardGameUpdateRequest {
    @NotEmpty
    @JsonProperty("Id")
    private String id;

    @NotEmpty
    @JsonProperty("Name")
    private String name;

    @NotEmpty
    @JsonProperty("NumberOfPlayers")
    private String numberOfPlayers;

    @NotEmpty
    @JsonProperty("YearPublished")
    private String yearPublished;

    @NotEmpty
    @JsonProperty("AveragePlayTime")
    private String averagePlayTime;

    @NotEmpty
    @JsonProperty("CollectionId")
    private String collectionId;

    public String getId() {
        return null;
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
