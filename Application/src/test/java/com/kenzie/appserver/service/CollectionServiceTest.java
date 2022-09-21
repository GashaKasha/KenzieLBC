package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.model.CollectionRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kenzie.appserver.service.model.Collection;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    private CollectionService collectionService;

    @BeforeEach
    void beforeEach(){
        MockitoAnnotations.initMocks(this);
        collectionService = new CollectionService(collectionRepository);
    }

    @Test
    void addCollection_validCollection_concertIsReturnedWithCorrectData() {
        // GIVEN
        String collectionId = randomUUID().toString();
        String collectionName = "testName";
        String collectionType = "MagicTheGathering";
        String collectionDescription = "great description";
        List<String> collectionCards = new ArrayList<>();
        collectionCards.add("goodCard1");
        collectionCards.add("goodCard2");
        collectionCards.add("reallyGoodCard1");
        collectionCards.add("kindOfOkCardButLooksCool1");
        collectionCards.add("notGoodCard1");
        collectionCards.add("expensiveCard1");
        String collectionCreationDate = ZonedDateTime.now().toString();

        Collection collection = new Collection(collectionId, collectionName, collectionType, collectionDescription, collectionCards, collectionCreationDate);

        ArgumentCaptor<CollectionRecord> collectionRecordCaptor = ArgumentCaptor.forClass(CollectionRecord.class);

        // WHEN
        Collection returnedCollection = collectionService.addCollection(collection);

        // THEN
        Assertions.assertNotNull(returnedCollection);

        verify(collectionRepository).save(collectionRecordCaptor.capture());

        CollectionRecord record = collectionRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The concert record is returned");
        Assertions.assertEquals(record.getId(), collection.getId(), "The collection id matches");
        Assertions.assertEquals(record.getCollectionName(), collection.getCollectionName(), "The collection name matches");
        Assertions.assertEquals(record.getType(), collection.getType(), "The collection type matches");
        Assertions.assertEquals(record.getDescription(), collection.getDescription(), "The collection description matches");
        Assertions.assertEquals(record.getCollectionItemNames(), collection.getCollectionItemNames(), "The collection items match");
        Assertions.assertEquals(record.getCreationDate(), collection.getCreationDate(), "the collection creation date matches");
    }

    @Test
    void addCollection_emptyCollectionId_IllegalArgumentExceptionIsThrown() {
        // GIVEN
        String collectionId = randomUUID().toString();
        String collectionName = "";
        String collectionType = "MagicTheGathering";
        String collectionDescription = "great description";
        List<String> collectionCards = new ArrayList<>();
        collectionCards.add("goodCard1");
        collectionCards.add("goodCard2");
        collectionCards.add("reallyGoodCard1");
        collectionCards.add("kindOfOkCardButLooksCool1");
        collectionCards.add("notGoodCard1");
        collectionCards.add("expensiveCard1");
        String collectionCreationDate = ZonedDateTime.now().toString();

        Collection collection = new Collection(collectionId, collectionName, collectionType, collectionDescription, collectionCards, collectionCreationDate);
        CollectionRecord collectionRecord = new CollectionRecord();
        collectionRecord.setId(collection.getId());
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(collection.getCollectionItemNames());
        collectionRecord.setCreationDate(collection.getCreationDate());

        when(collectionRepository.save(collectionRecord)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class,
                ()-> collectionService.addCollection(collection),
                "expected IllegalArgumentException to be thrown when null Id is entered to be saved to database.");

    }
}
