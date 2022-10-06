package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.model.CollectionRecord;
import com.kenzie.appserver.service.model.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository){
        this.collectionRepository = collectionRepository;
    }

    public Collection addCollection(Collection collection){
        if(collection == null){
            throw new IllegalArgumentException();
        }
        CollectionRecord collectionRecord = new CollectionRecord();
        collectionRecord.setId(collection.getId());
        collectionRecord.setCreationDate(collection.getCreationDate());
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(collection.getCollectionItemNames());
        collectionRepository.save(collectionRecord);
        return collection;
    }


    public Collection getCollectionById(String collectionId){
        Collection collectionFromBackEnd = collectionRepository
                .findById(collectionId)
                .map(collection -> new Collection( collection.getId(),
                        collection.getCreationDate(),
                        collection.getCollectionName(),
                        collection.getType(),
                        collection.getDescription(),
                        collection.getCollectionItemNames()))
                .orElse(null);
        return collectionFromBackEnd;
    }

    //removeById
    public void deleteCollectionById(String collectionId){
        if(collectionRepository.existsById(collectionId)){
            collectionRepository.deleteById(collectionId);
        }else{
            throw new IllegalArgumentException(String.format("Collection Id %s does not exist", collectionId));
        }
    }

    //addItemToList
    public void addItemToList(String collectionId, String itemName){
        //not doing a exists by Id, because in order to create card a valid collectionId must be input
        // TODO: Add a check to verify that the item doesn't already exists in the list
        // Maybe needs to be a custom exception for - if names already exists in collection
        if(collectionId == null || itemName == null){
            throw new IllegalArgumentException();
        }

        Collection collection = getCollectionById(collectionId);
        List<String> itemList = collection.getCollectionItemNames();
        itemList.add(itemName);

        CollectionRecord collectionRecord = new CollectionRecord();
        collectionRecord.setId(collection.getId());
        collectionRecord.setCreationDate(collection.getCreationDate());
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(itemList);
        collectionRepository.save(collectionRecord);
    }

    public List<Collection> getAllCollections(){
        List<Collection> listOfCollections = new ArrayList<>();
        Iterable<CollectionRecord> collectionofCollections = collectionRepository.findAll();
        for (CollectionRecord record:collectionofCollections) {
            Collection collection = new Collection(
                    record.getId(),
                    record.getCreationDate(),
                    record.getCollectionName(),
                    record.getType(),
                    record.getDescription(),
                    record.getCollectionItemNames());
            listOfCollections.add(collection);
        }
        return listOfCollections;
    }

    public boolean doesExist(String collectionId) {
        // Returns true if collectionId exists, otherwise False
        return collectionRepository.existsById(collectionId);
    }

    public boolean checkCollectionItemNames(String collectionId) {
        Collection getCollection = getCollectionById(collectionId);

        return !getCollection.getCollectionItemNames().isEmpty();
    }

//    public void deleteItemFromList(String collectionId, String itemName){
//        if(collectionId == null || itemName == null){
//            throw new IllegalArgumentException();
//        }
//        Collection collection = getCollectionById(collectionId);
//        List<String> itemList = collection.getCollectionItemNames();
//        itemList.remove(itemName);
//
//        CollectionRecord collectionRecord = new CollectionRecord();
//        collectionRecord.setId(collection.getId());
//        collectionRecord.setCollectionName(collection.getCollectionName());
//        collectionRecord.setType(collection.getType());
//        collectionRecord.setDescription(collection.getDescription());
//        collectionRecord.setCollectionItemNames(itemList);
//        collectionRecord.setCreationDate(collection.getCreationDate());
//        collectionRepository.save(collectionRecord);
//    }
}
