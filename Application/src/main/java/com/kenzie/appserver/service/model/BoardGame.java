package com.kenzie.appserver.service.model;

public class BoardGame {

    private final String id;
    private final String name;
    private final String numberOfPlayers;
    private final String yearPublished;
    private final String averagePlayTime;
    private final String collectionId;

    public BoardGame(String id, String name, String numberOfPlayers, String yearPublished, String averagePlayTime, String collectionId) {
        this.id = id;
        this.name = name;
        this.numberOfPlayers = numberOfPlayers;
        this.yearPublished = yearPublished;
        this.averagePlayTime = averagePlayTime;
        this.collectionId = collectionId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public String getAveragePlayTime() {
        return averagePlayTime;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
