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

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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
//        collectionCards.add("goodCard1");
//        collectionCards.add("goodCard2");
//        collectionCards.add("reallyGoodCard1");
//        collectionCards.add("kindOfOkCardButLooksCool1");
//        collectionCards.add("notGoodCard1");
//        collectionCards.add("expensiveCard1");
        String collectionCreationDate = ZonedDateTime.now().toString();

        Collection collection = new Collection(collectionId, collectionName, collectionType, collectionDescription, collectionCreationDate, collectionCards);

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

        assertThrows(IllegalArgumentException.class,
                ()-> collectionService.addCollection(null),
                "expected IllegalArgumentException to be thrown when null Id is entered to be saved to database.");

    }

    @Test
    void findByCollectionId() throws IllegalAccessException {
        // GIVEN
        String collecitonId = randomUUID().toString();
        List<String> collectionCards = new ArrayList<>();
        collectionCards.add("goodCard1");
        collectionCards.add("goodCard2");
        collectionCards.add("reallyGoodCard1");
        collectionCards.add("kindOfOkCardButLooksCool1");
        collectionCards.add("notGoodCard1");
        collectionCards.add("expensiveCard1");

        CollectionRecord record = new CollectionRecord();
        record.setId(collecitonId);
        record.setCollectionName("collectionName");
        record.setType("MagicTheGathering");
        record.setCollectionItemNames(collectionCards);
        record.setDescription("coolDeck");
        record.setCreationDate(LocalDateTime.now().toString());
        when(collectionRepository.findById(collecitonId)).thenReturn(Optional.of(record));
        // WHEN
        Collection collection = collectionService.getCollectionById(collecitonId);

        // THEN
        Assertions.assertNotNull(collection, "The collection is returned");
        Assertions.assertEquals(record.getId(), collection.getId(), "The collection id matches");
        Assertions.assertEquals(record.getCollectionName(), collection.getCollectionName(), "The collection name matches");
        Assertions.assertEquals(record.getType(), collection.getType(), "The collection type matches");
        Assertions.assertEquals(record.getCollectionItemNames(), collection.getCollectionItemNames(), "The collection item list matches");
        Assertions.assertEquals(record.getDescription(),collection.getDescription(), "The collection description matches");
        Assertions.assertEquals(record.getCreationDate(),collection.getCreationDate(), "The collection creation date matches");
    }

    @Test
    void deleteCollection_validCollectionId_collectionDeleted() throws IllegalAccessException {
        String collectionId = UUID.randomUUID().toString();

        when(collectionRepository.existsById(collectionId)).thenReturn(true);

        collectionService.deleteCollectionById(collectionId);

        verify(collectionRepository, times(1)).deleteById(collectionId);
    }

    @Test
    void deleteCollection_invalidCollectionId_IllegalArgumentExceptionThrown() throws IllegalAccessException {

        Assertions.assertThrows(IllegalArgumentException.class,
                ()-> collectionService.deleteCollectionById(null),
                "IllegalArgumentException should be thrown if existsById is false");
    }
}
