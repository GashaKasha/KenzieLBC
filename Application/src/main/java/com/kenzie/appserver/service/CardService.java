package com.kenzie.appserver.service;


import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.MagicTheGatheringRepository;
import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void updateCardInCollection(MagicTheGathering magicTheGathering) {
        if (magicTheGathering == null) {
            throw new IllegalArgumentException();
        }
        String collectionId = magicTheGathering.getCollectionId();
        if (doesExist(collectionId)) {
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

            collectionService.addItemToList(collectionId, magicTheGathering.getName());
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean doesExist(String collectionId) {
        // Returns true if collectionId exists, otherwise False
        return collectionService.doesExist(collectionId);
    }
}
