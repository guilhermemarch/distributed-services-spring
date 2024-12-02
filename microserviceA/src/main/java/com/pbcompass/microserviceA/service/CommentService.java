package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;
import com.pbcompass.microserviceA.entity.Comment;
import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.feign.PostClient;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private PostClient commentClient;

    public CommentDTO fetchCommentByPostIdAndCommentId(Long postId, Long commentId) {
        return commentClient.fetchCommentByPostIdAndCommentId(postId, commentId);
    }

    public Comment updateComment(Long postId, Long commentId, Comment comment) {
        Post post = commentClient.fetchOptionalPostById(postId)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + postId));

        Comment commentUpd = commentClient.fetchOptionalCommentId(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("No comment found with the id " + commentId));

        if (!post.getComments().contains(commentUpd)) {
            throw new ObjectNotFoundException("No comment found with the id " + commentId + " in post with id " + postId);
        }

        commentUpd.getPostId();
        commentUpd.getId();
        commentUpd.setName(comment.getName());
        commentUpd.setEmail(comment.getEmail());
        commentUpd.setBody(comment.getEmail());
        return commentClient.updateComment(postId, commentId, commentUpd);
    }

    public List<CommentDTO> fetchCommentsByPostId(long postId) {
        return commentClient.fetchCommentsByPostId(postId);
    }

    public Comment createComment(Long postId, Comment comment) {
        return commentClient.createComment(postId, comment);
    }


    public void deleteComment(Long postId, Long commentId) {
        Post post = commentClient.fetchOptionalPostById(postId)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + postId));

        Comment comment = commentClient.fetchOptionalCommentId(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("No comment found with the id " + commentId));

        if (!post.getComments().contains(comment)) {
            throw new ObjectNotFoundException("No comment found with the id " + commentId + " in post with id " + postId);
        }


        post.getComments().remove(comment);
        commentClient.deleteByPostIdAndCommentId(postId, commentId);
    }
}
