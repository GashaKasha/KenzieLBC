package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.BoardGameCreateRequest;
import com.kenzie.appserver.controller.model.BoardGameResponse;
import com.kenzie.appserver.controller.model.BoardGameUpdateRequest;
import com.kenzie.appserver.service.BoardGameService;
import com.kenzie.appserver.service.model.BoardGame;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/boardGame")
public class BoardGameController {
    private BoardGameService boardGameService;

    BoardGameController(BoardGameService boardGameService){
        this.boardGameService = boardGameService;
    }

    @PostMapping()
    public ResponseEntity<BoardGameResponse> addBoardGameToCollection(@RequestBody BoardGameCreateRequest boardGameCreateRequest){
        String collectionId = boardGameCreateRequest.getCollectionId();

        if(!boardGameService.checkIfCollectionIdExists(collectionId)) {
            return ResponseEntity.notFound().build();
        }

        String boardGameId = UUID.randomUUID().toString();

        BoardGame boardGame = new BoardGame(boardGameId,
                boardGameCreateRequest.getName(),
                boardGameCreateRequest.getNumberOfPlayers(),
                boardGameCreateRequest.getYearPublished(),
                boardGameCreateRequest.getAveragePlayTime(),
                boardGameCreateRequest.getCollectionId());

        boardGameService.addBoardGameToCollection(boardGame);

        BoardGameResponse boardGameResponse = createBoardGameResponse(boardGame);

        return ResponseEntity.created(URI.create("/boardGame" + boardGameResponse.getCollectionId())).body(boardGameResponse);
    }

//    @PutMapping
//    public ResponseEntity<BoardGameResponse> updateBoardGame(@RequestBody BoardGameUpdateRequest boardGameUpdateRequest) {
//        BoardGame boardGame = new BoardGame(boardGameUpdateRequest.getId(),
//                boardGameUpdateRequest.getName(),
//                boardGameUpdateRequest.getNumberOfPlayers(),
//                boardGameUpdateRequest.getYearPublished(),
//                boardGameUpdateRequest.getAveragePlayTime(),
//                boardGameUpdateRequest.getCollectionId());
//        boardGameService.updateBoardGame(boardGame);
//
//        BoardGameResponse boardGameResponse = createBoardGameResponse(boardGame);
//
//        return ResponseEntity.ok(boardGameResponse);
//    }

    private BoardGameResponse createBoardGameResponse(BoardGame boardGame) {
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        boardGameResponse.setId(boardGame.getId());
        boardGameResponse.setName(boardGame.getName());
        boardGameResponse.setNumberOfPlayers(boardGame.getNumberOfPlayers());
        boardGameResponse.setYearPublished(boardGame.getYearPublished());
        boardGameResponse.setAveragePlayTime(boardGame.getAveragePlayTime());
        boardGameResponse.setCollectionId(boardGame.getCollectionId());

        return boardGameResponse;
    }
}
