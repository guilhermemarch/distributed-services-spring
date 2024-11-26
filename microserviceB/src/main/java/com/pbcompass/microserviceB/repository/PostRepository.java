package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {

}
