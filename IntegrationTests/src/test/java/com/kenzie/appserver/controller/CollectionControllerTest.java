package com.kenzie.appserver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CollectionCreateRequest;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CollectionControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CollectionService collectionService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void createCollection_withValidCollection_createsNewCollection() throws Exception {
        // GIVEN
        String collectionName = mockNeat.strings().valStr();
        String type = "Board Game";
        String description = "Patti's Board Game Collection";

        CollectionCreateRequest collectionCreateRequest = new CollectionCreateRequest();
        collectionCreateRequest.setCollectionName(collectionName);
        collectionCreateRequest.setType(type);
        collectionCreateRequest.setDescription(description);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/collections")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(collectionCreateRequest)))

        // THEN
                .andExpect(jsonPath("collectionId")
                        .exists())
                .andExpect(status().isCreated());
    }

    // TODO: Unhappy case = createCollection with emptyCollection throws an exception - Testing Properly?
    @Test
    public void createCollection_withEmptyCollection_doesNotCreateCollection() throws Exception {
        // GIVEN
        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/collections")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(null)))

                // THEN
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getCollectionById_withValidCollectionId_returnsCollection() throws Exception {
        // GIVEN
        String collectionId = UUID.randomUUID().toString();
        String collectionDate = LocalDate.now().toString();
        String collectionName = mockNeat.strings().valStr();
        String type = "Card Game";
        String description = "Patti's MTG Collection";
        List<String> collectionItemNames = new ArrayList<>();

        Collection collection = new Collection(collectionId, collectionDate, collectionName, type, description, collectionItemNames);
        Collection newCollection = collectionService.addCollection(collection);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(get("/collections/{collectionId}", newCollection.getId())
                        .accept(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(jsonPath("collectionId")
                        .value(is(collectionId)))
                .andExpect(jsonPath("creationDate")
                        .value(is(collectionDate)))
                .andExpect(jsonPath("collectionName")
                        .value(is(collectionName)))
                .andExpect(jsonPath("type")
                        .value(is(type)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(jsonPath("collectionItemNames")
                        .value(is(collectionItemNames)))
                .andExpect(status().isOk());
    }

    @Test
    public void getCollectionById_withInvalidCollectionId_collectionDoesNotExist() throws Exception {
        // GIVEN
        String collectionId = UUID.randomUUID().toString();

        // WHEN
        mvc.perform(get("/collections/{collectionId}", collectionId)
                        .accept(MediaType.APPLICATION_JSON))

        // THEN
                .andExpect(status().isNotFound());

    }

    @Test
    public void deleteCollectionById_withValidCollectionId_deletesCollection() throws Exception {
        // GIVEN
        String collectionId = UUID.randomUUID().toString();
        String collectionDate = LocalDate.now().toString();
        String collectionName = mockNeat.strings().valStr();
        String type = "Card Game";
        String description = "Patti's Omnath Commander Deck Collection";
        List<String> collectionItemNames = new ArrayList<>();

        Collection collection = new Collection(collectionId, collectionDate, collectionName, type, description, collectionItemNames);
        Collection newCollection = collectionService.addCollection(collection);

        // WHEN
        mvc.perform(delete("/collections/{collectionId}", newCollection.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertThat(collectionService.getCollectionById(collectionId)).isNull();
    }

    @Test
    public void deleteCollectionById_withInvalidCollectionId_doesNotDeleteCollection() throws Exception {
        // GIVEN
        String collectionId = UUID.randomUUID().toString();
        String collectionDate = LocalDate.now().toString();
        String collectionName = mockNeat.strings().valStr();
        String type = "Board Game";
        String description = "Fresh in the pack BGs Collection";
        List<String> collectionItemNames = new ArrayList<>();

        Collection collection = new Collection(collectionId, collectionDate, collectionName, type, description, collectionItemNames);
        collectionService.addCollection(collection);
        String collectionId2 = UUID.randomUUID().toString();

        // WHEN
        mvc.perform(get("/collections/{collectionId}", collectionId2)
                        .accept(MediaType.APPLICATION_JSON))

        // THEN
                .andExpect(status().isNotFound());
        assertThat(collectionService.getCollectionById(collectionId)).isNotNull();
    }

    @Test
    public void getAllCollections_2Collections_returns2Collections() throws Exception {
        // GIVEN
        String collectionId = UUID.randomUUID().toString();
        String collectionDate = LocalDate.now().toString();
        String collectionName = mockNeat.strings().valStr();
        String type = "Card Game";
        String description = "Patti's MTG Collection";
        List<String> collectionItemNames = new ArrayList<>();

        Collection collection = new Collection(collectionId, collectionDate, collectionName, type, description, collectionItemNames);
        Collection newCollection = collectionService.addCollection(collection);

        String collectionId2 = UUID.randomUUID().toString();
        String collectionDate2 = LocalDate.now().toString();
        String collectionName2 = mockNeat.strings().valStr();
        String type2 = "Card Game";
        String description2 = "Jake's MTG Collection";
        List<String> collectionItemNames2 = new ArrayList<>();

        Collection collection1 = new Collection(collectionId2, collectionDate2, collectionName2, type2, description2, collectionItemNames2);
        Collection newCollection2 = collectionService.addCollection(collection1);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(get("/collections")
                        .accept(MediaType.APPLICATION_JSON))

                // THEN
                .andExpect(status().isOk());
    }

    // TODO: Add method to clean up test data in the database
    // Add method here or in service class?
}
