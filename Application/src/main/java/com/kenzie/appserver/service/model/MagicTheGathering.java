package com.kenzie.appserver.service.model;

import java.util.List;

public class MagicTheGathering {

    private final String id;
    private final String name;
    private final List<String> releasedSet;
    private final String cardType;
    private final String manaCost;
    private final String powerToughness;
    private final String cardAbilities;
    private final int numberOfCardsOwned;
    private final String artist;
    private final String collectionId;

    public MagicTheGathering(String id,
                             String name,
                             List<String> releasedSet,
                             String cardType,
                             String manaCost,
                             String powerToughness,
                             String cardAbilities,
                             int numberOfCardsOwned,
                             String artist,
                             String collectionId) {
        this.id = id;
        this.name = name;
        this.releasedSet = releasedSet;
        this.cardType = cardType;
        this.manaCost = manaCost;
        this.powerToughness = powerToughness;
        this.cardAbilities = cardAbilities;
        this.numberOfCardsOwned = numberOfCardsOwned;
        this.artist = artist;
        this.collectionId = collectionId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getReleasedSet() {
        return releasedSet;
    }

    public String getCardType() {
        return cardType;
    }

    public String getManaCost() {
        return manaCost;
    }

    public String getPowerToughness() {
        return powerToughness;
    }

    public String getCardAbilities() {
        return cardAbilities;
    }

    public int getNumberOfCardsOwned() {
        return numberOfCardsOwned;
    }

    public String getArtist() {
        return artist;
    }

    public String getCollectionId() {
        return collectionId;
    }
}
