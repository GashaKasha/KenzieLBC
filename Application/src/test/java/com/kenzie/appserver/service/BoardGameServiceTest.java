package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.BoardGameRepository;
import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.model.BoardGameRecord;
import com.kenzie.appserver.service.model.BoardGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BoardGameServiceTest {
    private BoardGameRepository boardGameRepository;
    private BoardGameService boardGameService;

    @BeforeEach
    void setup() {
        boardGameRepository = mock(BoardGameRepository.class);
        boardGameService = new BoardGameService(boardGameRepository);
    }

    @Test
    void addBoardGame_validData_addsGame(){
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

        ArgumentCaptor<BoardGameRecord> boardGameRecordArgumentCaptor = ArgumentCaptor.forClass(BoardGameRecord.class);

        // WHEN
        BoardGame returnedBoardGame = boardGameService.addBoardGame(validBoardGame);

        // THEN
        Assertions.assertNotNull(returnedBoardGame);
        verify(boardGameRepository).save(boardGameRecordArgumentCaptor.capture());
        BoardGameRecord boardGameRecord = boardGameRecordArgumentCaptor.getValue();

        Assertions.assertNotNull(boardGameRecord, "The board game record is returned");
        Assertions.assertEquals(boardGameRecord.getId(), validBoardGame.getId(), "The board game id matches.");
        Assertions.assertEquals(boardGameRecord.getName(), validBoardGame.getName(), "The board game name matches.");
        Assertions.assertEquals(boardGameRecord.getNumberOfPlayers(), validBoardGame.getNumberOfPlayers(), "The board game number of players matches.");
        Assertions.assertEquals(boardGameRecord.getYearPublished(), validBoardGame.getYearPublished(), "The board game year published matches.");
        Assertions.assertEquals(boardGameRecord.getAveragePlayTime(), validBoardGame.getAveragePlayTime(), "The board game average play time matches.");
        Assertions.assertEquals(boardGameRecord.getCollectionId(), validBoardGame.getCollectionId(), "The board game collection id matches.");
    }

    @Test
    void addBoardGame_emptyStringName_returnsNull(){
        // GIVEN + WHEN + THEN
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> boardGameService.addBoardGame(null),
                "Null board game should throw IllegalArgumentException");
    }
}
