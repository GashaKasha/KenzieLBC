package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CollectionRepository;
import com.kenzie.appserver.repositories.model.CollectionRecord;
import com.kenzie.appserver.service.model.Collection;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {

    private CollectionRepository collectionRepository;

    @Autowired
    public CollectionService(CollectionRepository collectionRepository){
        this.collectionRepository = collectionRepository;
    }

    public Collection addCollection(Collection collection){
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
}
