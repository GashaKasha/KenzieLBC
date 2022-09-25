package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.BoardGameResponse;
import com.kenzie.appserver.controller.model.BoardGameUpdateRequest;
import com.kenzie.appserver.service.BoardGameService;
import com.kenzie.appserver.service.model.BoardGame;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/boardGame")
public class BoardGameController {
    private BoardGameService boardGameService;

    BoardGameController(BoardGameService boardGameService){
        this.boardGameService = boardGameService;
    }

    @PutMapping
    public ResponseEntity<BoardGameResponse> updateBoardGame(@RequestBody BoardGameUpdateRequest boardGameUpdateRequest) {
        BoardGame boardGame = new BoardGame(boardGameUpdateRequest.getId(),
                boardGameUpdateRequest.getName(),
                boardGameUpdateRequest.getNumberOfPlayers(),
                boardGameUpdateRequest.getYearPublished(),
                boardGameUpdateRequest.getAveragePlayTime(),
                boardGameUpdateRequest.getCollectionId());
        boardGameService.updateBoardGame(boardGame);

        BoardGameResponse boardGameResponse = createBoardGameResponse(boardGame);

        return ResponseEntity.ok(boardGameResponse);
    }

    private BoardGameResponse createBoardGameResponse(BoardGame boardGame) {
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        boardGameResponse.setId(boardGame.getId());
        boardGameResponse.setName(boardGame.getName());
        boardGameResponse.setNumberOfPlayers(boardGame.getNumberOfPlayers());
        boardGameResponse.setYearPublished(boardGame.getAveragePlayTime());
        boardGameResponse.setAveragePlayTime(boardGame.getAveragePlayTime());
        boardGameResponse.setCollectionId(boardGame.getCollectionId());

        return boardGameResponse;
    }
}
