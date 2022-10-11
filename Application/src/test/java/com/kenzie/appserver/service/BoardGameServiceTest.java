package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.BoardGameRepository;
import com.kenzie.appserver.repositories.model.BoardGameRecord;
import com.kenzie.appserver.service.model.BoardGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class BoardGameServiceTest {

    private BoardGameService boardGameService;

    @Mock
    private BoardGameRepository boardGameRepository;

    @Mock
    private CollectionService collectionService;

    @BeforeEach
    void setup() {
        // The below declaration was causing the collectionService is null error
        //boardGameRepository = mock(BoardGameRepository.class);
        MockitoAnnotations.initMocks(this);
        boardGameService = new BoardGameService(boardGameRepository, collectionService);
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
        BoardGame returnedBoardGame = boardGameService.addBoardGameToCollection(validBoardGame);

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
    void addBoardGame_nullBoardGame_throwsException(){
        // GIVEN + WHEN + THEN
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> boardGameService.addBoardGameToCollection(null),
                "Null board game should throw IllegalArgumentException");
    }

    @Test
    void findAllBoardGame_twoBoardGames_twoBoardGamesReturned(){
        BoardGameRecord record1 = new BoardGameRecord();
        record1.setId(UUID.randomUUID().toString());
        record1.setName("fakeName1");
        record1.setNumberOfPlayers("2");
        record1.setYearPublished("1994");
        record1.setAveragePlayTime("A While");
        record1.setCollectionId(UUID.randomUUID().toString());

        BoardGameRecord record2 = new BoardGameRecord();
        record2.setId(UUID.randomUUID().toString());
        record2.setName("fakeName2");
        record2.setNumberOfPlayers("3");
        record2.setYearPublished("1993");
        record2.setAveragePlayTime("Really Long");
        record2.setCollectionId(UUID.randomUUID().toString());

        List<BoardGameRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(boardGameRepository.findAll()).thenReturn(recordList);

        List<BoardGame> boardGames = boardGameService.getAllBoardGames();

        Assertions.assertNotNull(boardGames,"The board game list is null");
        Assertions.assertEquals(2, boardGames.size(), "boardGames should have a size of 2");

        for (BoardGame boardGame: boardGames) {
            if(boardGame.getId().equals(record1.getId())){
                Assertions.assertEquals(record1.getId(), boardGame.getId(), "the Id's should match");
                Assertions.assertEquals(record1.getName(), boardGame.getName(), "the Names should match");
                Assertions.assertEquals(record1.getNumberOfPlayers(), boardGame.getNumberOfPlayers(), "numberOfPlayers should match");
                Assertions.assertEquals(record1.getYearPublished(), boardGame.getYearPublished(), "yearPublished should match");
                Assertions.assertEquals(record1.getAveragePlayTime(), boardGame.getAveragePlayTime(), "averagePlayTime should match");
                Assertions.assertEquals(record1.getCollectionId(), boardGame.getCollectionId(), "collectionId should match");
            }else if (boardGame.getId().equals(record2.getId())){
                Assertions.assertEquals(record2.getId(), boardGame.getId(), "the Id's should match");
                Assertions.assertEquals(record2.getName(), boardGame.getName(), "the Names should match");
                Assertions.assertEquals(record2.getNumberOfPlayers(), boardGame.getNumberOfPlayers(), "numberOfPlayers should match");
                Assertions.assertEquals(record2.getYearPublished(), boardGame.getYearPublished(), "yearPublished should match");
                Assertions.assertEquals(record2.getAveragePlayTime(), boardGame.getAveragePlayTime(), "averagePlayTime should match");
                Assertions.assertEquals(record2.getCollectionId(), boardGame.getCollectionId(), "collectionId should match");
            }
        }
    }
}
