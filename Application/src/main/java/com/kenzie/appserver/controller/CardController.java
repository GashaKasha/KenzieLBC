package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CardCreateRequest;
import com.kenzie.appserver.controller.model.CardResponse;
import com.kenzie.appserver.service.CardService;
import com.kenzie.appserver.service.CollectionService;
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
    private CollectionService collectionService;

    public CardController(CardService cardService) { this.cardService = cardService; }

    @PostMapping("/mtg")
    public ResponseEntity<CardResponse> addMtgCardToCollection(@RequestBody CardCreateRequest cardCreateRequest) {
        // Check if collection Id exists
        String collectionId = cardCreateRequest.getCollectionId();
        if (!collectionService.doesExist(collectionId)) {
            return ResponseEntity.noContent().build();
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

    private CardResponse createCardResponse(MagicTheGathering magicTheGathering) {
        CardResponse cardResponse = new CardResponse();
        cardResponse.setCollectionId(magicTheGathering.getCollectionId());
        return cardResponse;
    }
}
