package com.kenzie.appserver.controller;

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
    public void createCollection_withValidCollectionId_createsNewCollection() throws Exception {
        // GIVEN
        String creationDate = LocalDate.now().toString();
        String collectionName = mockNeat.strings().valStr();
        String type = "Board Game";
        String description = "Patti's Board Game Collection";
        List<String> collectionItems = new ArrayList<>();

        CollectionCreateRequest collectionCreateRequest = new CollectionCreateRequest();
        collectionCreateRequest.setCreationDate(LocalDate.now());
        collectionCreateRequest.setCollectionName(collectionName);
        collectionCreateRequest.setType(type);
        collectionCreateRequest.setDescription(description);
        collectionCreateRequest.setCollectionItemNames(collectionItems);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(post("/collections")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(collectionCreateRequest)))

        // THEN
                .andExpect(jsonPath("collectionId")
                        .exists())
                .andExpect(jsonPath("creationDate")
                        .value(is(creationDate)))
                .andExpect(jsonPath("collectionName")
                        .value(is(collectionName)))
                .andExpect(jsonPath("type")
                        .value(is(type)))
                .andExpect(jsonPath("description")
                        .value(is(description)))
                .andExpect(status().isCreated());
    }

//    @Test
//    public void createCollection_withValidCollectionId_createsNewCollection() throws Exception {
//        // GIVEN
//        String collectionId = UUID.randomUUID().toString();
//        String collectionDate = LocalDate.now().toString();
//        String collectionName = mockNeat.strings().valStr();
//        String type = "Board Game";
//        String description = "Patti's BG Collection";
//
//        Collection collection = new Collection(collectionId, collectionDate, collectionName, type, description);
//        Collection newCollection = collectionService.addCollection(collection);
//
//        // WHEN
//        mvc.perform(get)
//
//        // THEN
//    }
}
