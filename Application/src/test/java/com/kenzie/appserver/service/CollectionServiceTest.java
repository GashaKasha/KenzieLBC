package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.model.BoardGameRecord;
import com.kenzie.appserver.repositories.model.CollectionRecord;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kenzie.appserver.service.model.Collection;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CollectionServiceTest {

    @Mock
    private CollectionRepository collectionRepository;

    private CollectionService collectionService;

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.initMocks(this);
        collectionService = new CollectionService(collectionRepository);
    }

    @Test
    void addCollection_validCollection_collectionIsReturnedWithCorrectData() {
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

        Collection collection = new Collection(collectionId, collectionCreationDate, collectionName, collectionType, collectionDescription, collectionCards);

        ArgumentCaptor<CollectionRecord> collectionRecordCaptor = ArgumentCaptor.forClass(CollectionRecord.class);

        // WHEN
        Collection returnedCollection = collectionService.addCollection(collection);

        // THEN
        Assertions.assertNotNull(returnedCollection);

        verify(collectionRepository).save(collectionRecordCaptor.capture());

        CollectionRecord record = collectionRecordCaptor.getValue();

        Assertions.assertNotNull(record, "The collection record is returned");
        Assertions.assertEquals(record.getId(), collection.getId(), "The collection id matches");
        Assertions.assertEquals(record.getCreationDate(), collection.getCreationDate(), "the collection creation date matches");
        Assertions.assertEquals(record.getCollectionName(), collection.getCollectionName(), "The collection name matches");
        Assertions.assertEquals(record.getType(), collection.getType(), "The collection type matches");
        Assertions.assertEquals(record.getDescription(), collection.getDescription(), "The collection description matches");
        Assertions.assertEquals(record.getCollectionItemNames(), collection.getCollectionItemNames(), "The collection items match");
    }

    @Test
    void addCollection_emptyCollectionId_IllegalArgumentExceptionIsThrown() {
        // GIVEN

        assertThrows(IllegalArgumentException.class,
                () -> collectionService.addCollection(null),
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
        record.setCreationDate(LocalDateTime.now().toString());
        record.setCollectionName("collectionName");
        record.setType("MagicTheGathering");
        record.setDescription("coolDeck");
        record.setCollectionItemNames(collectionCards);
        when(collectionRepository.findById(collecitonId)).thenReturn(Optional.of(record));
        // WHEN
        Collection collection = collectionService.getCollectionById(collecitonId);

        // THEN
        Assertions.assertNotNull(collection, "The collection is returned");
        Assertions.assertEquals(record.getId(), collection.getId(), "The collection id matches");
        Assertions.assertEquals(record.getCreationDate(), collection.getCreationDate(), "The collection creation date matches");
        Assertions.assertEquals(record.getCollectionName(), collection.getCollectionName(), "The collection name matches");
        Assertions.assertEquals(record.getType(), collection.getType(), "The collection type matches");
        Assertions.assertEquals(record.getDescription(), collection.getDescription(), "The collection description matches");
        Assertions.assertEquals(record.getCollectionItemNames(), collection.getCollectionItemNames(), "The collection item list matches");
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
                () -> collectionService.deleteCollectionById(null),
                "IllegalArgumentException should be thrown if existsById is false");
    }

    @Test
    void addItemToList_validItem_itemAddedToList() {
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
        Collection collection = new Collection(collectionId, collectionCreationDate, collectionName, collectionType, collectionDescription, collectionCards);
        ArgumentCaptor<CollectionRecord> collectionRecordCaptor = ArgumentCaptor.forClass(CollectionRecord.class);
        // WHEN
        Collection returnedCollection = collectionService.addCollection(collection);
        // THEN
        Assertions.assertNotNull(returnedCollection);
        CollectionRecord collectionRecord = new CollectionRecord();
        collectionRecord.setId(collection.getId());
        collectionRecord.setCreationDate(collection.getCreationDate());
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(collection.getCollectionItemNames());
        when(collectionRepository.findById(collectionId)).thenReturn(Optional.of(collectionRecord));
        collectionService.addItemToList(collectionId, "addedName");
        verify(collectionRepository, times(2)).save(collectionRecordCaptor.capture());
        CollectionRecord record = collectionRecordCaptor.getValue();
        Assertions.assertNotNull(record, "The collection record is returned");
        Assertions.assertEquals(record.getId(), collection.getId(), "The collection id matches");
        Assertions.assertEquals(record.getCreationDate(), collection.getCreationDate(), "the collection creation date matches");
        Assertions.assertEquals(record.getCollectionName(), collection.getCollectionName(), "The collection name matches");
        Assertions.assertEquals(record.getType(), collection.getType(), "The collection type matches");
        Assertions.assertEquals(record.getDescription(), collection.getDescription(), "The collection description matches");
        assert (record.getCollectionItemNames().contains("addedName"));
    }

    @Test
    void findAllcollections_two_collections() {
        // GIVEN
        CollectionRecord record1 = new CollectionRecord();
        record1.setId(randomUUID().toString());
        record1.setCollectionName("fakeName1");
        record1.setDescription("Fun Description 1");
        record1.setType("a type");
        record1.setCreationDate(LocalDateTime.now().toString());
        record1.setCollectionItemNames(new ArrayList<>());

        CollectionRecord record2 = new CollectionRecord();
        record2.setId(randomUUID().toString());
        record2.setCollectionName("fakeName2");
        record2.setDescription("Fun Description 2");
        record2.setType("b type");
        record2.setCreationDate(LocalDateTime.now().toString());
        record2.setCollectionItemNames(new ArrayList<>());

        List<CollectionRecord> recordList = new ArrayList<>();
        recordList.add(record1);
        recordList.add(record2);
        when(collectionRepository.findAll()).thenReturn(recordList);

        // WHEN
        List<Collection> collections = collectionService.getAllCollections();

        // THEN
        Assertions.assertNotNull(collections, "The collection list is returned");
        Assertions.assertEquals(2, collections.size(), "There are two collections");

        for (Collection collection : collections) {
            if (collection.getId() == record1.getId()) {
                Assertions.assertEquals(record1.getId(), collection.getId(), "The collection id matches");
                Assertions.assertEquals(record1.getCollectionName(), collection.getCollectionName(), "The collection name matches");
                Assertions.assertEquals(record1.getType(), collection.getType(), "The collection type matches");
                Assertions.assertEquals(record1.getDescription(), collection.getDescription(), "The collection description matches");
                Assertions.assertEquals(record1.getCreationDate(), collection.getCreationDate(), "The collection creation date matches");
            } else if (collection.getId() == record2.getId()) {
                Assertions.assertEquals(record2.getId(), collection.getId(), "The collection id matches");
                Assertions.assertEquals(record2.getCollectionName(), collection.getCollectionName(), "The collection name matches");
                Assertions.assertEquals(record2.getType(), collection.getType(), "The collection type matches");
                Assertions.assertEquals(record2.getDescription(), collection.getDescription(), "The collection description matches");
                Assertions.assertEquals(record2.getCreationDate(), collection.getCreationDate(), "The collection creation date matches");
            } else {
                Assertions.assertTrue(false, "collection returned that was not in the records!");
            }
        }
    }
}
