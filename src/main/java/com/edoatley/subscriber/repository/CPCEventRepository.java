package com.edoatley.subscriber.repository;

import com.edoatley.subscriber.model.CPCEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPCEventRepository extends MongoRepository<CPCEvent, String> {
}
