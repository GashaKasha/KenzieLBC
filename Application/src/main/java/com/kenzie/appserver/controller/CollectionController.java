package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CollectionCreateRequest;
import com.kenzie.appserver.controller.model.CollectionResponse;
import com.kenzie.appserver.controller.model.CollectionGetResponse;
import com.kenzie.appserver.service.CollectionService;
import com.kenzie.appserver.service.model.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        LocalDate creationDate = LocalDate.now();
        List<String> collectionItems = new ArrayList<>();

        Collection collection = new Collection(generateCollectionId,
                creationDate.toString(),
                collectionCreateRequest.getCollectionName(),
                collectionCreateRequest.getType(),
                collectionCreateRequest.getDescription(),
                collectionItems);
        collectionService.addCollection(collection);

        CollectionResponse collectionResponse = createCollectionResponse(collection);

        return ResponseEntity.created(URI.create("/collections/" + collectionResponse.getCollectionId())).body(collectionResponse);
    }

    // getCollectionById()
    @GetMapping("/{collectionId}")
    public ResponseEntity<CollectionGetResponse> getCollectionById(@PathVariable("collectionId") String collectionId) {
        if (collectionId == null || collectionId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Collection collection = collectionService.getCollectionById(collectionId);

        if (collection == null) {
            return ResponseEntity.notFound().build();
        }

        CollectionGetResponse collectionGetResponse = getCollectionResponse(collection);
        return ResponseEntity.ok(collectionGetResponse);
    }

    // deleteCollectionById()
    @DeleteMapping("/{collectionId}")
    public ResponseEntity deleteCollectionById(@PathVariable("collectionId") String collectionId) {
        if (collectionId == null || collectionId.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        collectionService.deleteCollectionById(collectionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CollectionGetResponse>> getAllCollections() {
        List<Collection> collections = collectionService.getAllCollections();

        if (collections == null ||  collections.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CollectionGetResponse> response = new ArrayList<>();
        for (Collection collection : collections) {
            response.add(this.getCollectionResponse(collection));
        }

        return ResponseEntity.ok(response);
    }

    private CollectionResponse createCollectionResponse(Collection collection) {
        CollectionResponse collectionResponse = new CollectionResponse();
        collectionResponse.setCollectionId(collection.getId());
        return collectionResponse;
    }

    private CollectionGetResponse getCollectionResponse(Collection collection) {
        CollectionGetResponse collectionGetResponse = new CollectionGetResponse();
        collectionGetResponse.setCollectionId(collection.getId());
        collectionGetResponse.setCreationDate(collection.getCreationDate());
        collectionGetResponse.setCollectionName(collection.getCollectionName());
        collectionGetResponse.setType(collection.getType());
        collectionGetResponse.setDescription(collection.getDescription());
        collectionGetResponse.setCollectionItemNames(collection.getCollectionItemNames());
        return collectionGetResponse;
    }
}
