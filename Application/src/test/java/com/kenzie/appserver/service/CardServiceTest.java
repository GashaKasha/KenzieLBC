package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MagicTheGatheringRepository;
import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class CardServiceTest {

    @Mock
    private MagicTheGatheringRepository magicTheGatheringRepository;

    private CardService cardService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        cardService = new CardService(magicTheGatheringRepository);
    }

    @Test
    void addCard_validMagicCard_cardAdded() {
        String id = randomUUID().toString();
        String name = "Arcanis the Omnipotent";
        List<String> releasedSet = new ArrayList<>();
        releasedSet.add("Onslaught");
        releasedSet.add("Tenth Edition");
        releasedSet.add("Duel Decks: Speed vs. Cunning");
        releasedSet.add("Eternal Masters");
        releasedSet.add("Commander 2017");
        String cardType = "Legendary Creature - Wizard";
        String manaCost = "3UUU";
        String powerToughness = "3/4";
        String description = "Tap: Draw three cards. 2UU: Return Arcanis the Omnipotent" +
                " to its owner's hand.";
        int numOfCardsOwned = 4;
        String artist = "Justin Sweet";
        String collectionId = randomUUID().toString();

        MagicTheGathering card = new MagicTheGathering(id, name, releasedSet, cardType,
                manaCost, powerToughness, description, numOfCardsOwned, artist, collectionId);

        ArgumentCaptor<MagicTheGatheringRecord> cardCaptor = ArgumentCaptor.forClass(MagicTheGatheringRecord.class);

        // WHEN
        MagicTheGathering returnedCard = cardService.addCardToCollection(card);

        // THEN
        assertNotNull(returnedCard);

        verify(magicTheGatheringRepository).save(cardCaptor.capture());

        MagicTheGatheringRecord record = cardCaptor.getValue();

        assertNotNull(record, "The card is returned.");
        assertEquals(record.getId(), card.getId());
        assertEquals(record.getName(), card.getName());
        assertEquals(record.getReleasedSet(), card.getReleasedSet());
        assertEquals(record.getCardType(), card.getCardType());
        assertEquals(record.getManaCost(), card.getManaCost());
        assertEquals(record.getPowerToughness(), card.getPowerToughness());
        assertEquals(record.getDescription(), card.getDescription());
        assertEquals(record.getNumberOfCardsOwned(), card.getNumberOfCardsOwned());
        assertEquals(record.getArtist(), card.getArtist());
        assertEquals(record.getCollectionId(), card.getCollectionId());
    }

    @Test
    void addCardToCollection_emptyCollectionId_IllegalArgumentExceptionIsThrown() {
        // GIVEN + WHEN + THEN

        assertThrows(IllegalArgumentException.class,
                ()-> cardService.addCardToCollection(null),
                "expected IllegalArgumentException to be thrown when null Id is entered to be saved to database.");

    }
}
