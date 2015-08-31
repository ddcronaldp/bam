package com.dealertrack.admiral.persistence.repository;

import com.dealertrack.admiral.persistence.po.ChangeSetPO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChangeSetRepository extends MongoRepository<ChangeSetPO, String> {
//    List<ChangeSetPO> findByUser(String user);
}
