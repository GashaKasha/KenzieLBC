package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.MagicTheGatheringRepository;
import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private MagicTheGatheringRepository magicTheGatheringRepository;

    @Autowired
    public CardService(MagicTheGatheringRepository magicTheGatheringRepository) {
        this.magicTheGatheringRepository = magicTheGatheringRepository;
    }

    public MagicTheGathering addCardToCollection(MagicTheGathering magicTheGathering) {
        if (magicTheGathering == null) {
            throw new IllegalArgumentException();
        }
        MagicTheGatheringRecord magicTheGatheringRecord = new MagicTheGatheringRecord();
        magicTheGatheringRecord.setId(magicTheGathering.getId());
        magicTheGatheringRecord.setName(magicTheGathering.getName());
        magicTheGatheringRecord.setReleasedSet(magicTheGathering.getReleasedSet());
        magicTheGatheringRecord.setCardType(magicTheGathering.getCardType());
        magicTheGatheringRecord.setManaCost(magicTheGathering.getManaCost());
        magicTheGatheringRecord.setPowerToughness(magicTheGathering.getPowerToughness());
        magicTheGatheringRecord.setDescription(magicTheGathering.getDescription());
        magicTheGatheringRecord.setNumberOfCardsOwned(magicTheGathering.getNumberOfCardsOwned());
        magicTheGatheringRecord.setArtist(magicTheGathering.getArtist());
        magicTheGatheringRecord.setCollectionId(magicTheGathering.getCollectionId());
        magicTheGatheringRepository.save(magicTheGatheringRecord);
        return magicTheGathering;
    }

    public void updateCardInCollection(MagicTheGathering magicTheGathering) {
        if (magicTheGathering == null) {
            throw new IllegalArgumentException();
        }
        MagicTheGatheringRecord magicTheGatheringRecord = new MagicTheGatheringRecord();
        magicTheGatheringRecord.setId(magicTheGathering.getId());
        magicTheGatheringRecord.setName(magicTheGathering.getName());
        magicTheGatheringRecord.setReleasedSet(magicTheGathering.getReleasedSet());
        magicTheGatheringRecord.setCardType(magicTheGathering.getCardType());
        magicTheGatheringRecord.setManaCost(magicTheGathering.getManaCost());
        magicTheGatheringRecord.setPowerToughness(magicTheGathering.getPowerToughness());
        magicTheGatheringRecord.setDescription(magicTheGathering.getDescription());
        magicTheGatheringRecord.setNumberOfCardsOwned(magicTheGathering.getNumberOfCardsOwned());
        magicTheGatheringRecord.setArtist(magicTheGathering.getArtist());
        magicTheGatheringRecord.setCollectionId(magicTheGathering.getCollectionId());
        magicTheGatheringRepository.save(magicTheGatheringRecord);
    }
}
