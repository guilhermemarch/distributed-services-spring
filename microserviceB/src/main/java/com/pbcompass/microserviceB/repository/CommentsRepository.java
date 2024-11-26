package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends MongoRepository<Comments, String> {

}
