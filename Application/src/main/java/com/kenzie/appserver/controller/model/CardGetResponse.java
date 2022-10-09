package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardGetResponse {

    @JsonProperty("Id")
    private String Id;

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

    @JsonProperty("collectionId")
    private String collectionId;

    public void setId(String id) {
        Id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleasedSet(List<String> releasedSet) {
        this.releasedSet = releasedSet;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public void setPowerToughness(String powerToughness) {
        this.powerToughness = powerToughness;
    }

    public void setCardAbilities(String cardAbilities) {
        this.cardAbilities = cardAbilities;
    }

    public void setNumberOfCardsOwned(int numberOfCardsOwned) {
        this.numberOfCardsOwned = numberOfCardsOwned;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
}
