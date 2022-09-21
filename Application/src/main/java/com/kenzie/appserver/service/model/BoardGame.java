package com.kenzie.appserver.service.model;

public class BoardGame {

    private final String id;
    private final String name;
    private final int numberOfPlayers;
    private final String yearPublished;
    private final Double averagePlayTime;
    private final String collectionId;

    public BoardGame(String id, String name, int numberOfPlayers, String yearPublished, Double averagePlayTime, String collectionId) {
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

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getYearPublished() {
        return yearPublished;
    }

    public Double getAveragePlayTime() {
        return averagePlayTime;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
