package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    Comment findTopByOrderByDocumentIdDesc();

    Optional<Comment> findById(Long id);

    void deleteById(Long id);

}
