package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.BoardGameUpdateRequest;
import com.kenzie.appserver.service.BoardGameService;
import com.kenzie.appserver.service.model.BoardGame;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@IntegrationTest
//class ConcertControllerTest {
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    ConcertService concertService;
//
//    private final MockNeat mockNeat = MockNeat.threadLocal();
//
//    private final ObjectMapper mapper = new ObjectMapper();
@IntegrationTest
public class BoardGameControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    BoardGameService boardGameService;

    //    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void updateBoardGame_validData_putSuccessful() throws Exception{
        // GIVEN
        String id = UUID.randomUUID().toString();
        String name = "Testham Horror: The Test Game";
        String numberOfPlayers = "2";
        String yearPublished = "2016";
        String averagePlayTime = "60-120";
        String collectionId = UUID.randomUUID().toString();

        BoardGame validBoardGame = new BoardGame(
                id,
                name,
                numberOfPlayers,
                yearPublished,
                averagePlayTime,
                collectionId);
        //        BoardGame persistedBoardGame = boardGameService.addBoardGame(validBoardGame);

        String newNumberOfPlayers = "2-4";
        String newCollectionId = UUID.randomUUID().toString();

        BoardGameUpdateRequest boardGameUpdateRequest = new BoardGameUpdateRequest();
        boardGameUpdateRequest.setCollectionId(id);
        boardGameUpdateRequest.setName(name);
        boardGameUpdateRequest.setNumberOfPlayers(newNumberOfPlayers);
        boardGameUpdateRequest.setYearPublished(yearPublished);
        boardGameUpdateRequest.setAveragePlayTime(averagePlayTime);
        boardGameUpdateRequest.setCollectionId(newCollectionId);

        //        mapper.registerModule(new JavaTimeModule());

        // WHEN
        mvc.perform(put("/boardGame")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(boardGameUpdateRequest)))
        // THEN
                .andExpect()
    }
//    public void updateConcert_PutSuccessful() throws Exception {
//        // GIVEN
//        String id = UUID.randomUUID().toString();
//        String name = mockNeat.strings().valStr();
//        String date = LocalDate.now().toString();
//        Double ticketBasePrice = 90.0;
//
//        Concert concert = new Concert(id, name, date, ticketBasePrice, false);
//        Concert persistedConcert = concertService.addNewConcert(concert);
//
//        String newName = mockNeat.strings().valStr();
//        Double newTicketBasePrice = 100.0;
//
//        ConcertUpdateRequest concertUpdateRequest = new ConcertUpdateRequest();
//        concertUpdateRequest.setId(id);
//        concertUpdateRequest.setDate(LocalDate.now());
//        concertUpdateRequest.setName(newName);
//        concertUpdateRequest.setTicketBasePrice(newTicketBasePrice);
//        concertUpdateRequest.setReservationClosed(true);
//
//        mapper.registerModule(new JavaTimeModule());
//
//        // WHEN
//        mvc.perform(put("/concerts")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(concertUpdateRequest)))
//                // THEN
//                .andExpect(jsonPath("id")
//                        .exists())
//                .andExpect(jsonPath("name")
//                        .value(is(newName)))
//                .andExpect(jsonPath("date")
//                        .value(is(date)))
//                .andExpect(jsonPath("ticketBasePrice")
//                        .value(is(newTicketBasePrice)))
//                .andExpect(jsonPath("reservationClosed")
//                        .value(is(true)))
//                .andExpect(status().isOk());
//    }
}
