//package com.kenzie.appserver.controller;
//
//public class CardController {
//import com.kenzie.appserver.controller.model.CardCreateRequest;
//import com.kenzie.appserver.controller.model.CardResponse;
//import com.kenzie.appserver.service.CardService;
//import com.kenzie.appserver.service.CollectionService;
//import com.kenzie.appserver.service.model.MagicTheGathering;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/cards")
//public class CardController {
//
//    private CardService cardService;
//
//    public CardController(CardService cardService) {
//        this.cardService = cardService;
//    }
//
//    @PostMapping("/mtg")
//    public ResponseEntity<CardResponse> addMtgCardToCollection(@RequestBody CardCreateRequest cardCreateRequest) {
//        // Check if collection Id exists
//        // TODO: This should actually return an error message or something
//        String collectionId = cardCreateRequest.getCollectionId();
//        if (!cardService.doesExist(collectionId)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        String generateCardId = UUID.randomUUID().toString();
//
//        MagicTheGathering magicTheGathering = new MagicTheGathering(generateCardId,
//                cardCreateRequest.getName(),
//                cardCreateRequest.getReleasedSet(),
//                cardCreateRequest.getCardType(),
//                cardCreateRequest.getManaCost(),
//                cardCreateRequest.getPowerToughness(),
//                cardCreateRequest.getCardAbilities(),
//                cardCreateRequest.getNumberOfCardsOwned(),
//                cardCreateRequest.getArtist(),
//                cardCreateRequest.getCollectionId());
//
//        cardService.addCardToCollection(magicTheGathering);
//        CardResponse cardResponse = createCardResponse(magicTheGathering);
//
//        return ResponseEntity.created(URI.create("/cards/mtg" + cardResponse.getCollectionId())).body(cardResponse);
//    }
//
//    private CardResponse createCardResponse(MagicTheGathering magicTheGathering) {
//        CardResponse cardResponse = new CardResponse();
//        cardResponse.setName(magicTheGathering.getName());
//        cardResponse.setCollectionId(magicTheGathering.getCollectionId());
//        return cardResponse;
//    }
//}
