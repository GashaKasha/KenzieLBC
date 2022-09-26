package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CardCreateRequest;
import com.kenzie.appserver.service.CardService;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
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
        String cardId = UUID.randomUUID().toString();
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

        assertThat(collectionService.)
    }

    // TODO: Unhappy case addMtgCardToCollection_withInvalidCollectionId
}
