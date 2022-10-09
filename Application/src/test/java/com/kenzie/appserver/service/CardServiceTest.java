package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MagicTheGatheringRepository;
import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;
import com.kenzie.appserver.service.model.Collection;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CardServiceTest {

    private CardService cardService;

    @Mock
    private MagicTheGatheringRepository magicTheGatheringRepository;

    @Mock
    private CollectionService collectionService;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        cardService = new CardService(magicTheGatheringRepository, collectionService);
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
        String cardAbilities = "Tap: Draw three cards. 2UU: Return Arcanis the Omnipotent" +
                " to its owner's hand.";
        int numOfCardsOwned = 4;
        String artist = "Justin Sweet";
        String collectionId = randomUUID().toString();

        MagicTheGathering card = new MagicTheGathering(id, name, releasedSet, cardType,
                manaCost, powerToughness, cardAbilities, numOfCardsOwned, artist, collectionId);

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
        assertEquals(record.getCardAbilities(), card.getCardAbilities());
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

    @Test
    void getAllCards_TwoCards_TwoCardsReturned(){
        MagicTheGatheringRecord record1 = new MagicTheGatheringRecord();
        record1.setId(randomUUID().toString());
        record1.setName("fakeName1");
        record1.setReleasedSet(new ArrayList<String>());
        record1.setCardType("a card type");
        record1.setManaCost("a mana cost");
        record1.setPowerToughness("really tough");
        record1.setCardAbilities("what cool abilities");
        record1.setNumberOfCardsOwned(3);
        record1.setArtist("Gold Face");
        record1.setCollectionId(randomUUID().toString());

        MagicTheGatheringRecord record2 = new MagicTheGatheringRecord();
        record2.setId(randomUUID().toString());
        record2.setName("fakeName2");
        record2.setReleasedSet(new ArrayList<String>());
        record2.setCardType("b card type");
        record2.setManaCost("b mana cost");
        record2.setPowerToughness("really powerful");
        record2.setCardAbilities("what dumb abilities");
        record2.setNumberOfCardsOwned(1);
        record2.setArtist("Michael Scarn");
        record2.setCollectionId(randomUUID().toString());

        List<MagicTheGatheringRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(magicTheGatheringRepository.findAll()).thenReturn(recordList);

        List<MagicTheGathering> magicTheGatherings = cardService.getAllCards();

        assertNotNull(magicTheGatherings, "The card list is returned");
        assertEquals(2, magicTheGatherings.size(), "There are two cards");

        for (MagicTheGathering magicTheGathering : magicTheGatherings) {
            if (magicTheGathering.getId().equals(record1.getId())) {
                assertEquals(record1.getId(), magicTheGathering.getId(), "The card id matches");
                Assertions.assertEquals(record1.getName(), magicTheGathering.getName(), "The card name matches");
                Assertions.assertEquals(record1.getReleasedSet(), magicTheGathering.getReleasedSet(), "The card Released Set should matche");
                Assertions.assertEquals(record1.getCardType(), magicTheGathering.getCardType(), "The card type should match");
                Assertions.assertEquals(record1.getManaCost(), magicTheGathering.getManaCost(), "The card mana cost should match");
                Assertions.assertEquals(record1.getPowerToughness(), magicTheGathering.getPowerToughness(), "The card powerToughness should match");
                Assertions.assertEquals(record1.getCardAbilities(), magicTheGathering.getCardAbilities(), "The cardAbilities should match");
                Assertions.assertEquals(record1.getNumberOfCardsOwned(), magicTheGathering.getNumberOfCardsOwned(), "The numberOfCardsOwned should match");
                Assertions.assertEquals(record1.getArtist(), magicTheGathering.getArtist(), "The card Artist should match");
                Assertions.assertEquals(record1.getCollectionId(), magicTheGathering.getCollectionId(), "The card collectionId should match");
            } else if (magicTheGathering.getId().equals(record2.getId())) {
                Assertions.assertEquals(record2.getName(), magicTheGathering.getName(), "The card name matches");
                Assertions.assertEquals(record2.getReleasedSet(), magicTheGathering.getReleasedSet(), "The card Released Set should matche");
                Assertions.assertEquals(record2.getCardType(), magicTheGathering.getCardType(), "The card type should match");
                Assertions.assertEquals(record2.getManaCost(), magicTheGathering.getManaCost(), "The card mana cost should match");
                Assertions.assertEquals(record2.getPowerToughness(), magicTheGathering.getPowerToughness(), "The card powerToughness should match");
                Assertions.assertEquals(record2.getCardAbilities(), magicTheGathering.getCardAbilities(), "The cardAbilities should match");
                Assertions.assertEquals(record2.getNumberOfCardsOwned(), magicTheGathering.getNumberOfCardsOwned(), "The numberOfCardsOwned should match");
                Assertions.assertEquals(record2.getArtist(), magicTheGathering.getArtist(), "The card Artist should match");
                Assertions.assertEquals(record2.getCollectionId(), magicTheGathering.getCollectionId(), "The card collectionId should match");
            } else {
                assertTrue(false, "collection returned that was not in the records!");
            }
        }
    }
}
