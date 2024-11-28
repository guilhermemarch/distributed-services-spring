package com.pbcompass.microserviceB.repository;

import com.pbcompass.microserviceB.entity.Comment;
import com.pbcompass.microserviceB.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

    Comment findTopByOrderByDocumentIdDesc();
}
