package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.BoardGameRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface BoardGameRepository extends CrudRepository<BoardGameRecord, String> {
    BoardGameRecord findByNameCollectionId(String name, String collectionId);
}
