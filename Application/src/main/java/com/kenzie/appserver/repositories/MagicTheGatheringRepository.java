package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.MagicTheGatheringRecord;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface MagicTheGatheringRepository extends CrudRepository<MagicTheGatheringRecord, String> {
}
