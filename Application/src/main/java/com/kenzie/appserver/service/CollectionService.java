package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.model.CollectionRecord;
import com.kenzie.appserver.service.model.Collection;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(collection.getCollectionItemNames());
        collectionRecord.setCreationDate(collection.getCreationDate());
        collectionRepository.save(collectionRecord);
        return collection;
    }


    public Collection getCollectionById(String collectionId){
        Collection collectionFromBackEnd = collectionRepository
                .findById(collectionId)
                .map(collection -> new Collection( collection.getId(),
                        collection.getCollectionName(),
                        collection.getType(),
                        collection.getDescription(),
                        collection.getCollectionItemNames(),
                        collection.getCreationDate()))
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
        if(collectionId == null || itemName == null){
            throw new IllegalArgumentException();
        }
        Collection collection = getCollectionById(collectionId);
        List<String> itemList = collection.getCollectionItemNames();
        itemList.add(itemName);

        CollectionRecord collectionRecord = new CollectionRecord();
        collectionRecord.setId(collection.getId());
        collectionRecord.setCollectionName(collection.getCollectionName());
        collectionRecord.setType(collection.getType());
        collectionRecord.setDescription(collection.getDescription());
        collectionRecord.setCollectionItemNames(itemList);
        collectionRecord.setCreationDate(collection.getCreationDate());
        collectionRepository.save(collectionRecord);
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
