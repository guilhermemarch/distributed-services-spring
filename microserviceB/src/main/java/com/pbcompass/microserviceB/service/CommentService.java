package com.pbcompass.microserviceB.service;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Comment;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.feign.CommentClient;
import com.pbcompass.microserviceB.feign.PostClient;
import com.pbcompass.microserviceB.repository.CommentRepository;
import com.pbcompass.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentClient commentClient;

    public Comment create(Comment comment) {
        Comment lastComment = commentRepository.findTopByOrderByDocumentIdDesc();
        if (lastComment == null) {
            comment.setId(1L);
        } else {
            Long nextId = lastComment.getId() + 1;
            comment.setId(nextId);
        }
        return commentRepository.save(comment);
    }

    public List<CommentDTO> findPostsJsonPlaceholder() {
        return commentClient.getComments();
    }

}
