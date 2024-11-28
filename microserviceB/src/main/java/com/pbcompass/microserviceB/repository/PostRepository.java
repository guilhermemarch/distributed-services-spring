package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Post findTopByOrderByDocumentIdDesc();

    Optional<Post> findById(Long id);

}
