package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.*;
import com.kenzie.appserver.service.CardService;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
import com.kenzie.appserver.service.model.MagicTheGathering;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
public class CardController {

    private CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/mtg")
    public ResponseEntity<CardResponse> addMtgCardToCollection(@RequestBody CardCreateRequest cardCreateRequest) {
        // Check if collection Id exists
        // TODO: This should actually return an error message or something
        String collectionId = cardCreateRequest.getCollectionId();
        if (!cardService.doesExist(collectionId)) {
            return ResponseEntity.notFound().build();
        }

        String generateCardId = UUID.randomUUID().toString();

        MagicTheGathering magicTheGathering = new MagicTheGathering(generateCardId,
                cardCreateRequest.getName(),
                cardCreateRequest.getReleasedSet(),
                cardCreateRequest.getCardType(),
                cardCreateRequest.getManaCost(),
                cardCreateRequest.getPowerToughness(),
                cardCreateRequest.getCardAbilities(),
                cardCreateRequest.getNumberOfCardsOwned(),
                cardCreateRequest.getArtist(),
                cardCreateRequest.getCollectionId());

        cardService.addCardToCollection(magicTheGathering);
        CardResponse cardResponse = createCardResponse(magicTheGathering);

        return ResponseEntity.created(URI.create("/cards/mtg" + cardResponse.getCollectionId())).body(cardResponse);
    }

    @GetMapping
    public ResponseEntity<List<CardGetResponse>> getAllMagicTheGathering() {
        List<MagicTheGathering> magicTheGathering = cardService.getAllCards();

        if (magicTheGathering == null || magicTheGathering.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CardGetResponse> response = new ArrayList<>();
        for (MagicTheGathering magicTheGathering1 : magicTheGathering) {
            response.add(this.createCardGetResponse(magicTheGathering1));
        }
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/mtg")
//    public ResponseEntity<CardResponse> updateCardInCollection(@RequestBody CardUpdateRequest cardUpdateRequest) {
//        String collectionId = cardUpdateRequest.getCollectionId();
//        if (!cardService.doesExist(collectionId)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        MagicTheGathering magicTheGathering = new MagicTheGathering(cardUpdateRequest.getId(),
//                cardUpdateRequest.getName(),
//                cardUpdateRequest.getReleasedSet(),
//                cardUpdateRequest.getCardType(),
//                cardUpdateRequest.getManaCost(),
//                cardUpdateRequest.getPowerToughness(),
//                cardUpdateRequest.getCardAbilities(),
//                cardUpdateRequest.getNumberOfCardsOwned(),
//                cardUpdateRequest.getArtist(),
//                cardUpdateRequest.getCollectionId());
//        cardService.updateCardInCollection(magicTheGathering);
//
//        CardResponse cardResponse = createCardResponse(magicTheGathering);
//
//        return ResponseEntity.ok(cardResponse);
//    }


    private CardResponse createCardResponse(MagicTheGathering magicTheGathering) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setName(magicTheGathering.getName());
        cardResponse.setCollectionId(magicTheGathering.getCollectionId());
        return cardResponse;
    }

    private CardGetResponse createCardGetResponse(MagicTheGathering magicTheGathering){
        CardGetResponse cardGetResponse = new CardGetResponse();
        cardGetResponse.setId(magicTheGathering.getId());
        cardGetResponse.setName(magicTheGathering.getName());
        cardGetResponse.setReleasedSet(magicTheGathering.getReleasedSet());
        cardGetResponse.setCardType(magicTheGathering.getCardType());
        cardGetResponse.setManaCost(magicTheGathering.getManaCost());
        cardGetResponse.setPowerToughness(magicTheGathering.getPowerToughness());
        cardGetResponse.setCardAbilities(magicTheGathering.getCardAbilities());
        cardGetResponse.setNumberOfCardsOwned(magicTheGathering.getNumberOfCardsOwned());
        cardGetResponse.setArtist(magicTheGathering.getArtist());
        cardGetResponse.setCollectionId(magicTheGathering.getCollectionId());
        return cardGetResponse;
    }
}
