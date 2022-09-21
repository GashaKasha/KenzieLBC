package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "MagicTheGathering")
public class MagicTheGatheringRecord {

    private String id;
    private String name;
    private List<String> releasedSet;
    private String cardType;
    private String manaCost;
    private String powerToughness;
    private int numberOfCardsOwned;
    private String artist;
    private String collectionId;

    @DynamoDBHashKey(attributeName = "Id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "ReleasedSet")
    public List<String> getReleasedSet() {
        return releasedSet;
    }

    public void setReleasedSet(List<String> releasedSet) {
        this.releasedSet = releasedSet;
    }

    @DynamoDBAttribute(attributeName = "CardType")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @DynamoDBAttribute(attributeName = "ManaCost")
    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    @DynamoDBAttribute(attributeName = "PowerToughness")
    public String getPowerToughness() {
        return powerToughness;
    }

    public void setPowerToughness(String powerToughness) {
        this.powerToughness = powerToughness;
    }

    @DynamoDBAttribute(attributeName = "NumberOfCardsOwned")
    public int getNumberOfCardsOwned() {
        return numberOfCardsOwned;
    }

    public void setNumberOfCardsOwned(int numberOfCardsOwned) {
        this.numberOfCardsOwned = numberOfCardsOwned;
    }

    @DynamoDBAttribute(attributeName = "Artist")
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @DynamoDBAttribute(attributeName = "CollectionId")
    public String getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagicTheGatheringRecord that = (MagicTheGatheringRecord) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
