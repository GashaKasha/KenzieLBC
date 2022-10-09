package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CardCreateRequest;
import com.kenzie.appserver.service.CardService;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
import com.kenzie.appserver.service.model.MagicTheGathering;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CardControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CardService cardService;

    @Autowired
    CollectionService collectionService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addMtgCardToCollection_withValidCollectionId_createsNewCardAndAddToCollection() throws Exception {
        // GIVEN
        // Create new Collection
        String collectionId = UUID.randomUUID().toString();
        String creationDate = LocalDate.now().toString();
        String collectionName = "Pat's MTG Library";
        String type = "Card Game";
        String description = "Pat MTG Card Collection";
        List<String> collectionItems = new ArrayList<>();

        Collection newCollection = new Collection(collectionId, creationDate, collectionName, type, description, collectionItems);
        collectionService.addCollection(newCollection);

        // Add Card to that Collection
        //String cardId = UUID.randomUUID().toString();
        String cardName = "Omnath, Locus of Creation";
        List<String> releasedSet = new ArrayList<>();
        releasedSet.add("Zendikar Rising");
        String cardType = "Creature";
        String manaCost = "RGWB";
        String powerToughness = "4/4";
        String cardAbilities = "Card Draw on ETB, Landfall, Lifegain, add RGWBU, deal damage.";
        int numberOfCardsOwned = 1;
        String artist = "Chris Rahn";

        CardCreateRequest cardCreateRequest = new CardCreateRequest();
        cardCreateRequest.setName(cardName);
        cardCreateRequest.setReleasedSet(releasedSet);
        cardCreateRequest.setCardType(cardType);
        cardCreateRequest.setManaCost(manaCost);
        cardCreateRequest.setPowerToughness(powerToughness);
        cardCreateRequest.setCardAbilities(cardAbilities);
        cardCreateRequest.setNumberOfCardsOwned(numberOfCardsOwned);
        cardCreateRequest.setArtist(artist);
        cardCreateRequest.setCollectionId(collectionId);

        mapper.registerModule(new JavaTimeModule());

        // TODO: This test or separate test should include
        // verifying that the card gets added to the Collection?
        // assertThat(collectionService.getCollectionById(collectionId)).isNull();
        // WHEN
        mvc.perform(post("/cards/mtg")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cardCreateRequest)))

        // THEN
                .andExpect(jsonPath("name")
                        .value(is(cardName)))
                .andExpect(jsonPath("collectionId")
                        .value(is(collectionId)))
                .andExpect(status().isCreated());

        assertThat(collectionService.checkCollectionItemNames(collectionId)).isTrue();
    }

    @Test
    public void addMtgCardToCollection_withInvalidCollectionId_doesNotCreateCard() throws Exception {
        // GIVEN

        // Create new Collection
        String collectionId = UUID.randomUUID().toString();
        String creationDate = LocalDate.now().toString();
        String collectionName = "Pat's MTG Library";
        String type = "Card Game";
        String description = "Pat MTG Card Collection";
        List<String> collectionItems = new ArrayList<>();

        Collection newCollection = new Collection(collectionId, creationDate, collectionName, type, description, collectionItems);
        collectionService.addCollection(newCollection);

        // Add Card to that Collection
        //String cardId = UUID.randomUUID().toString();
        String cardName = "Omnath, Locus of Creation";
        List<String> releasedSet = new ArrayList<>();
        releasedSet.add("Zendikar Rising");
        String cardType = "Creature";
        String manaCost = "RGWB";
        String powerToughness = "4/4";
        String cardAbilities = "Card Draw on ETB, Landfall, Lifegain, add RGWBU, deal damage.";
        int numberOfCardsOwned = 1;
        String artist = "Chris Rahn";

        CardCreateRequest cardCreateRequest = new CardCreateRequest();
        cardCreateRequest.setName(cardName);
        cardCreateRequest.setReleasedSet(releasedSet);
        cardCreateRequest.setCardType(cardType);
        cardCreateRequest.setManaCost(manaCost);
        cardCreateRequest.setPowerToughness(powerToughness);
        cardCreateRequest.setCardAbilities(cardAbilities);
        cardCreateRequest.setNumberOfCardsOwned(numberOfCardsOwned);
        cardCreateRequest.setArtist(artist);
        cardCreateRequest.setCollectionId(UUID.randomUUID().toString());

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/cards/mtg")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(cardCreateRequest)))

                // THEN
                // TODO: How to catch IllegalArgumentException in this manner?
                .andExpect(status().isNotFound());

        assertThat(collectionService.checkCollectionItemNames(collectionId)).isFalse();
    }

    @Test
    public void getAllCards_2Cards_returns2Cards() throws Exception {
        String collectionId = UUID.randomUUID().toString();
        String creationDate = LocalDate.now().toString();
        String collectionName = "Pat's MTG Library";
        String type = "Card Game";
        String description = "Pat MTG Card Collection";
        List<String> collectionItems = new ArrayList<>();

        Collection newCollection = new Collection(collectionId, creationDate, collectionName, type, description, collectionItems);
        collectionService.addCollection(newCollection);

        String id1 = UUID.randomUUID().toString();
        String name = "cardName1";
        List<String> releasedSet = new ArrayList<>();
        String cardType = "cardType1";
        String manaCost = "manaCost1";
        String powerToughness = "powerToughness1";
        String cardAbilities = "someCoolAbilities";
        int numberOfCardsOwned = 1;
        String artist = "Toby is the Scranton Strangler";

        MagicTheGathering magicTheGathering1 = new MagicTheGathering(
                id1,
                name,
                releasedSet,
                cardType,
                manaCost,
                powerToughness,
                cardAbilities,
                numberOfCardsOwned,
                artist,
                collectionId);
        cardService.addCardToCollection(magicTheGathering1);

        String id2 = UUID.randomUUID().toString();
        String name2 = "cardName2";
        List<String> releasedSet2 = new ArrayList<>();
        String cardType2 = "cardType2";
        String manaCost2 = "manaCost2";
        String powerToughness2 = "powerToughness2";
        String cardAbilities2 = "someLameAbilities";
        int numberOfCardsOwned2 = 2;
        String artist2 = "Dwight";

        MagicTheGathering magicTheGathering2 = new MagicTheGathering(
                id2,
                name2,
                releasedSet2,
                cardType2,
                manaCost2,
                powerToughness2,
                cardAbilities2,
                numberOfCardsOwned2,
                artist2,
                collectionId);
        cardService.addCardToCollection(magicTheGathering2);

        mapper.registerModule(new JavaTimeModule());

        mvc.perform(get("/cards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // TODO: Happy case for updateCardInCollection
//    @Test
//    public void updateCardInCollection_withValidCard_updatesCardData() throws Exception {
//        // GIVEN
//        // Create new Collection
//        String collectionId = UUID.randomUUID().toString();
//        String creationDate = LocalDate.now().toString();
//        String collectionName = "Pat's MTG Library";
//        String type = "Card Game";
//        String description = "Pat MTG Card Collection";
//        List<String> collectionItems = new ArrayList<>();
//
//        Collection newCollection = new Collection(collectionId, creationDate, collectionName, type, description, collectionItems);
//        collectionService.addCollection(newCollection);
//
//        // Original Card Data
//        String cardId = UUID.randomUUID().toString();
//        String cardName = "Omnath, Locus of Creation";
//        List<String> releasedSet = new ArrayList<>();
//        releasedSet.add("Zendikar Rising");
//        String cardType = "Creature";
//        String manaCost = "RGW";
//        String powerToughness = "3/4";
//        String cardAbilities = "Card Draw on ETB, Landfall, Lifegain, add RGWBU, deal damage.";
//        int numberOfCardsOwned = 1;
//        String artist = "Chris Rahn";
//
//        MagicTheGathering validMtgCard = new MagicTheGathering(
//                cardId,
//                cardName,
//                releasedSet,
//                cardType,
//                manaCost,
//                powerToughness,
//                cardAbilities,
//                numberOfCardsOwned,
//                artist,
//                collectionId
//        );
//
//        cardService.addCardToCollection(validMtgCard);
//
//        // Data to update
//        String correctManaCost = "RGWB";
//        String correctPowerToughness = "4/4";
//        int newCardOwned = 2;
//
//        CardUpdateRequest cardUpdateRequest = new CardUpdateRequest();
//        cardUpdateRequest.setId(cardId);
//        cardUpdateRequest.setName(cardName);
//        cardUpdateRequest.setReleasedSet(releasedSet);
//        cardUpdateRequest.setCardType(cardType);
//        cardUpdateRequest.setManaCost(correctManaCost);
//        cardUpdateRequest.setPowerToughness(correctPowerToughness);
//        cardUpdateRequest.setCardAbilities(cardAbilities);
//        cardUpdateRequest.setNumberOfCardsOwned(newCardOwned);
//        cardUpdateRequest.setArtist(artist);
//        cardUpdateRequest.setCollectionId(collectionId);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        // WHEN
//        mvc.perform(post("/cards/mtg")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(cardUpdateRequest)))
//
//                // THEN
//                .andExpect(jsonPath("name")
//                        .value(is(cardName)))
//                .andExpect(jsonPath("collectionId")
//                        .value(is(collectionId)))
//                .andExpect(status().isCreated());
//
//        assertThat(collectionService.checkCollectionItemNames(collectionId)).isTrue();
//
//    }
//
//    // TODO: Unhappy case for updateCardInCollection
//    @Test
//    public void updateCardInCollection_withInvalidCard_updatesCardData() throws Exception {
//        // GIVEN
//        // Create new Collection
//        String collectionId = UUID.randomUUID().toString();
//        String creationDate = LocalDate.now().toString();
//        String collectionName = "Pat's MTG Library";
//        String type = "Card Game";
//        String description = "Pat MTG Card Collection";
//        List<String> collectionItems = new ArrayList<>();
//
//        Collection newCollection = new Collection(collectionId, creationDate, collectionName, type, description, collectionItems);
//        collectionService.addCollection(newCollection);
//
//        // Original Card Data
//        String cardId = UUID.randomUUID().toString();
//        String cardName = "Omnath, Locus of Creation";
//        List<String> releasedSet = new ArrayList<>();
//        releasedSet.add("Zendikar Rising");
//        String cardType = "Creature";
//        String manaCost = "RGW";
//        String powerToughness = "3/4";
//        String cardAbilities = "Card Draw on ETB, Landfall, Lifegain, add RGWBU, deal damage.";
//        int numberOfCardsOwned = 1;
//        String artist = "Chris Rahn";
//
//        MagicTheGathering validMtgCard = new MagicTheGathering(
//                cardId,
//                cardName,
//                releasedSet,
//                cardType,
//                manaCost,
//                powerToughness,
//                cardAbilities,
//                numberOfCardsOwned,
//                artist,
//                collectionId
//        );
//
//        cardService.addCardToCollection(validMtgCard);
//
//        // Data to update
//        String correctManaCost = "RGWB";
//        String correctPowerToughness = "4/4";
//        int newCardOwned = 2;
//
//        CardUpdateRequest cardUpdateRequest = new CardUpdateRequest();
//        cardUpdateRequest.setId(cardId);
//        cardUpdateRequest.setName(cardName);
//        cardUpdateRequest.setReleasedSet(releasedSet);
//        cardUpdateRequest.setCardType(cardType);
//        cardUpdateRequest.setManaCost(correctManaCost);
//        cardUpdateRequest.setPowerToughness(correctPowerToughness);
//        cardUpdateRequest.setCardAbilities(cardAbilities);
//        cardUpdateRequest.setNumberOfCardsOwned(newCardOwned);
//        cardUpdateRequest.setArtist(artist);
//        cardUpdateRequest.setCollectionId(collectionId);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        // WHEN
//        mvc.perform(post("/cards/mtg")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(cardUpdateRequest)))
//
//                // THEN
//                .andExpect(jsonPath("name")
//                        .value(is(cardName)))
//                .andExpect(jsonPath("collectionId")
//                        .value(is(collectionId)))
//                .andExpect(status().isCreated());
//
//        assertThat(collectionService.checkCollectionItemNames(collectionId)).isTrue();
//
//    }
}
