package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.BoardGameRepository;
import com.kenzie.appserver.repositories.model.BoardGameRecord;
//import com.kenzie.appserver.repositories.model.CollectionRecord;
import com.kenzie.appserver.service.model.BoardGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardGameService {
    private BoardGameRepository boardGameRepository;
    private CollectionService collectionService;

    @Autowired
    public BoardGameService(BoardGameRepository boardGameRepository, CollectionService collectionService) {
        this.boardGameRepository = boardGameRepository;
        this.collectionService = collectionService;
    }

    public BoardGame addBoardGameToCollection(BoardGame boardGame){
        if(boardGame == null){
            throw new IllegalArgumentException();
        }

        String collectionId = boardGame.getCollectionId();

        if(checkIfCollectionIdExists(collectionId)) {
            BoardGameRecord boardGameRecord = new BoardGameRecord();
            boardGameRecord.setId(boardGame.getId());
            boardGameRecord.setName(boardGame.getName());
            boardGameRecord.setNumberOfPlayers(boardGame.getNumberOfPlayers());
            boardGameRecord.setYearPublished(boardGame.getYearPublished());
            boardGameRecord.setAveragePlayTime(boardGame.getAveragePlayTime());
            boardGameRecord.setCollectionId(boardGame.getCollectionId());
            boardGameRepository.save(boardGameRecord);
            collectionService.addItemToList(collectionId, boardGame.getName());
        } else {
            throw new IllegalArgumentException();
        }
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
    }

    public boolean checkIfCollectionIdExists(String collectionId){
        return collectionService.doesExist(collectionId);
    }
}
