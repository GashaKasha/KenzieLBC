package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CollectionCreateRequest;
import com.kenzie.appserver.controller.model.CollectionResponse;
import com.kenzie.appserver.service.BoardGameService;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/collections")
public class CollectionController {

    private CollectionService collectionService;

    public CollectionController(CollectionService collectionService) { this.collectionService = collectionService; }

    // API Endpoints


    // createCollection()
    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@RequestBody CollectionCreateRequest collectionCreateRequest) {

        String generateCollectionId = UUID.randomUUID().toString();
        List<String> collectionItems = new ArrayList<>();

        Collection collection = new Collection(generateCollectionId,
                collectionCreateRequest.getCreationDate().toString(),
                collectionCreateRequest.getCollectionName(),
                collectionCreateRequest.getType(),
                collectionCreateRequest.getDescription(),
                collectionItems);
        collectionService.addCollection(collection);

        CollectionResponse collectionResponse = createCollectionResponse(collection);

        return ResponseEntity.created(URI.create("/collections/" + collectionResponse.getCollectionId())).body(collectionResponse);
    }


    // getCollectionById()

    // deleteCollectionById()

    private CollectionResponse createCollectionResponse(Collection collection) {
        CollectionResponse collectionResponse = new CollectionResponse();
        collectionResponse.setCollectionId(collection.getId());
        return collectionResponse;
    }
}
