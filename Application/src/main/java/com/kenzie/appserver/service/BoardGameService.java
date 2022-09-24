package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.BoardGameRepository;
import com.kenzie.appserver.repositories.ExampleRepository;
import com.kenzie.appserver.repositories.model.BoardGameRecord;
//import com.kenzie.appserver.repositories.model.CollectionRecord;
import com.kenzie.appserver.service.model.BoardGame;

public class BoardGameService {
    private BoardGameRepository boardGameRepository;
//    private CacheStore cache;

    public BoardGameService(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    public BoardGame addBoardGame(BoardGame boardGame){
        if(boardGame == null){
            throw new IllegalArgumentException();
        }

        BoardGameRecord boardGameRecord = new BoardGameRecord();
        boardGameRecord.setId(boardGame.getId());
        boardGameRecord.setName(boardGame.getName());
        boardGameRecord.setNumberOfPlayers(boardGame.getNumberOfPlayers());
        boardGameRecord.setYearPublished(boardGame.getYearPublished());
        boardGameRecord.setAveragePlayTime(boardGame.getAveragePlayTime());
        boardGameRecord.setCollectionId(boardGame.getCollectionId());
        boardGameRepository.save(boardGameRecord);

        return boardGame;
    }

    public void updateBoardGame(BoardGame boardGame){
        if(boardGameRepository.existsById(boardGame.getId())){
            BoardGameRecord boardGameRecord = new BoardGameRecord();
            boardGameRecord.setId(boardGame.getId());
            boardGameRecord.setName(boardGame.getName());
            boardGameRecord.setNumberOfPlayers(boardGame.getNumberOfPlayers());
            boardGameRecord.setYearPublished(boardGame.getYearPublished());
            boardGameRecord.setAveragePlayTime(boardGame.getAveragePlayTime());
            boardGameRecord.setCollectionId(boardGame.getCollectionId());
            boardGameRepository.save(boardGameRecord);
        }
//        cache.evict(boardGame.getAveragePlayTime());
    }
}
