package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentsRepository extends MongoRepository<Comments, String> {
}
