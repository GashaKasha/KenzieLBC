package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CardCreateRequest {

    @NotEmpty
    @JsonProperty("name")
    private String name;

    @JsonProperty("releasedSet")
    private List<String> releasedSet;

    @JsonProperty("cardType")
    private String cardType;

    @JsonProperty("manaCost")
    private String manaCost;

    @JsonProperty("powerToughness")
    private String powerToughness;

    @JsonProperty("cardAbilities")
    private String cardAbilities;

    @JsonProperty("numberOfCardsOwned")
    private int numberOfCardsOwned;

    @JsonProperty("artist")
    private String artist;

    @NotEmpty
    @JsonProperty("collectionId")
    private String collectionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getReleasedSet() {
        return releasedSet;
    }

    public void setReleasedSet(List<String> releasedSet) {
        this.releasedSet = releasedSet;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getPowerToughness() {
        return powerToughness;
    }

    public void setPowerToughness(String powerToughness) {
        this.powerToughness = powerToughness;
    }

    public String getCardAbilities() {
        return cardAbilities;
    }

    public void setCardAbilities(String cardAbilities) {
        this.cardAbilities = cardAbilities;
    }

    public int getNumberOfCardsOwned() {
        return numberOfCardsOwned;
    }

    public void setNumberOfCardsOwned(int numberOfCardsOwned) {
        this.numberOfCardsOwned = numberOfCardsOwned;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
