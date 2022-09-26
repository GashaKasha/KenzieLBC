package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.BoardGameUpdateRequest;
import com.kenzie.appserver.service.BoardGameService;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.BoardGame;
import com.kenzie.appserver.service.model.Collection;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@IntegrationTest
public class BoardGameControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    BoardGameService boardGameService;

    @Autowired
    CollectionService collectionService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void addBoardGame_validData_postSuccessful() throws Exception{
        
    }

    @Test
    public void updateBoardGame_validData_putSuccessful() throws Exception{
        // GIVEN
        String id = UUID.randomUUID().toString();
        String name = "Testham Horror: The Test Game";
        String numberOfPlayers = "2";
        String yearPublished = "2015";
        String averagePlayTime = "60-120";
        String collectionId = UUID.randomUUID().toString();

        Collection collection = new Collection(collectionId,
                "Euro Games",
                "Board Game",
                "My favorite Euro style games",
                LocalDate.now().toString(),
                new ArrayList<>());

        collectionService.addCollection(collection);

        BoardGame validBoardGame = new BoardGame(
                id,
                name,
                numberOfPlayers,
                yearPublished,
                averagePlayTime,
                collectionId);

        boardGameService.addBoardGameToCollection(validBoardGame);

        String newNumberOfPlayers = "2-4";
        String newYearPublished = "2016";
//        String newCollectionId = UUID.randomUUID().toString();

        BoardGameUpdateRequest boardGameUpdateRequest = new BoardGameUpdateRequest();
        boardGameUpdateRequest.setId(id);
        boardGameUpdateRequest.setName(name);
        boardGameUpdateRequest.setNumberOfPlayers(newNumberOfPlayers);
        boardGameUpdateRequest.setYearPublished(newYearPublished);
        boardGameUpdateRequest.setAveragePlayTime(averagePlayTime);
        boardGameUpdateRequest.setCollectionId(collectionId);

        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/boardGame")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(boardGameUpdateRequest)))

        // THEN
                .andExpect(jsonPath("Id").exists())
                .andExpect(jsonPath("Name").value(is(name)))
                .andExpect(jsonPath("NumberOfPlayers").value(is(newNumberOfPlayers)))
                .andExpect(jsonPath("YearPublished").value(is(newYearPublished)))
                .andExpect(jsonPath("AveragePlayTime").value(is(averagePlayTime)))
                .andExpect(jsonPath("CollectionId").value(is(collectionId)))
                .andExpect(status().isOk());
    }
}
