package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;
import com.pbcompass.microserviceA.entity.Comment;
import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.feign.PostClient;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private PostClient commentClient;

    public List<CommentDTO> findCommentsByPostId(Long postId) {
        return commentClient.findCommentsByPostId(postId);
    }

    public CommentDTO findCommentById(Long commentId) {
        Optional<CommentDTO> comment = commentClient.findByCommentId(commentId);
        return comment.orElseThrow(() -> new ObjectNotFoundException("Comment not found"));
    }

    public CommentDTO createComment(Long postId, CommentDTO comment) {
        return commentClient.createComment(postId, comment);
    }

    public CommentDTO updateComment(Long postId, Long commentId, Comment comment) {
        PostDTO post = commentClient.findByPostId(postId)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + postId));

        CommentDTO commentUpd = commentClient.findByCommentId(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("No comment found with the id " + commentId));

        if (!commentUpd.getPostId().equals(postId)) {
            throw new ValidationException("The comment does not belong to the specified post.");
        }

        commentUpd.getPostId();
        commentUpd.getId();
        commentUpd.setName(comment.getName());
        commentUpd.setEmail(comment.getEmail());
        commentUpd.setBody(comment.getBody());
        return commentClient.updateComment(postId, commentId, commentUpd);
    }

    public void deleteComment(Long postId, Long commentId) {
        PostDTO post = commentClient.findByPostId(postId)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + postId));

        CommentDTO comment = commentClient.findByCommentId(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("No comment found with the id " + commentId));

        post.getComments().remove(comment);
        commentClient.deleteComment(postId, commentId);
    }

}
