package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.MagicTheGatheringRepository;
import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.util.ArrayList;
import java.util.List;

@Service
public class CardService {

    private MagicTheGatheringRepository magicTheGatheringRepository;
    private CollectionService collectionService;

//    @Autowired
//    public CardService(MagicTheGatheringRepository magicTheGatheringRepository) {
//        this.magicTheGatheringRepository = magicTheGatheringRepository;
//    }

    @Autowired
    public CardService(MagicTheGatheringRepository magicTheGatheringRepository, CollectionService collectionService) {
        this.magicTheGatheringRepository = magicTheGatheringRepository;
        this.collectionService = collectionService;
    }

    public MagicTheGathering addCardToCollection(MagicTheGathering magicTheGathering) {
        if (magicTheGathering == null) {
            throw new IllegalArgumentException();
        }

        String collectionId = magicTheGathering.getCollectionId();

        MagicTheGatheringRecord magicTheGatheringRecord = new MagicTheGatheringRecord();
        magicTheGatheringRecord.setId(magicTheGathering.getId());
        magicTheGatheringRecord.setName(magicTheGathering.getName());
        magicTheGatheringRecord.setReleasedSet(magicTheGathering.getReleasedSet());
        magicTheGatheringRecord.setCardType(magicTheGathering.getCardType());
        magicTheGatheringRecord.setManaCost(magicTheGathering.getManaCost());
        magicTheGatheringRecord.setPowerToughness(magicTheGathering.getPowerToughness());
        magicTheGatheringRecord.setCardAbilities(magicTheGathering.getCardAbilities());
        magicTheGatheringRecord.setNumberOfCardsOwned(magicTheGathering.getNumberOfCardsOwned());
        magicTheGatheringRecord.setArtist(magicTheGathering.getArtist());
        magicTheGatheringRecord.setCollectionId(magicTheGathering.getCollectionId());
        magicTheGatheringRepository.save(magicTheGatheringRecord);

        // Add to collection after creation
        collectionService.addItemToList(collectionId, magicTheGathering.getName());

        return magicTheGathering;
    }

    public List<MagicTheGathering> getAllCards(){
        List<MagicTheGathering> listOfCards = new ArrayList<>();
        Iterable<MagicTheGatheringRecord> records = magicTheGatheringRepository.findAll();
        for (MagicTheGatheringRecord record:records) {
            MagicTheGathering magicTheGathering = new MagicTheGathering(
                    record.getId(),
                    record.getName(),
                    record.getReleasedSet(),
                    record.getCardType(),
                    record.getManaCost(),
                    record.getPowerToughness(),
                    record.getCardAbilities(),
                    record.getNumberOfCardsOwned(),
                    record.getArtist(),
                    record.getCollectionId());
            listOfCards.add(magicTheGathering);
        }
        return listOfCards;
    }

//    public void updateCardInCollection(MagicTheGathering magicTheGathering) {
//        if (magicTheGathering == null) {
//            throw new IllegalArgumentException();
//        }
//        String collectionId = magicTheGathering.getCollectionId();
//        if (doesExist(collectionId)) {
//            MagicTheGatheringRecord magicTheGatheringRecord = new MagicTheGatheringRecord();
//            magicTheGatheringRecord.setId(magicTheGathering.getId());
//            magicTheGatheringRecord.setName(magicTheGathering.getName());
//            magicTheGatheringRecord.setReleasedSet(magicTheGathering.getReleasedSet());
//            magicTheGatheringRecord.setCardType(magicTheGathering.getCardType());
//            magicTheGatheringRecord.setManaCost(magicTheGathering.getManaCost());
//            magicTheGatheringRecord.setPowerToughness(magicTheGathering.getPowerToughness());
//            magicTheGatheringRecord.setCardAbilities(magicTheGathering.getCardAbilities());
//            magicTheGatheringRecord.setNumberOfCardsOwned(magicTheGathering.getNumberOfCardsOwned());
//            magicTheGatheringRecord.setArtist(magicTheGathering.getArtist());
//            magicTheGatheringRecord.setCollectionId(magicTheGathering.getCollectionId());
//            magicTheGatheringRepository.save(magicTheGatheringRecord);
//
//            collectionService.addItemToList(collectionId, magicTheGathering.getName());
//        } else {
//            throw new IllegalArgumentException();
//        }
//    }

    public boolean doesExist(String collectionId) {
        // Returns true if collectionId exists, otherwise False
        return collectionService.doesExist(collectionId);
    }
}
