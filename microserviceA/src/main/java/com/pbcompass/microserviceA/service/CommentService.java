package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.feign.CommentClient;
import com.pbcompass.microserviceB.service.exception.NoPostsFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    
    @Autowired
    private CommentClient commentClient;

    public CommentDTO fetchCommentByPostIdAndCommentId(Long id, Long commentId) {
        return commentClient.fetchCommentByPostIdAndCommentId(id, commentId);
    }

    public CommentDTO updatePost(Long postId, Long commentId, CommentDTO commentdto) {
        CommentDTO commentUpd = fetchCommentByPostIdAndCommentId(postId, commentId);
        commentUpd.getPostId();
        commentUpd.getId();
        commentUpd.setName(commentdto.getName());
        commentUpd.setEmail(commentdto.getEmail());
        commentUpd.setBody(commentdto.getEmail());
        return commentClient.updateComment(postId, commentId, commentUpd);
    }

    public void deleteByPostID(Long postId, Long commentId) {
        Optional<CommentDTO> comment = commentClient.fetchOptionalCommentByPostIdAndCommentId(postId, commentId);
        if (comment.isEmpty()) {
            throw new NoPostsFoundException(
                    "No posts found with the postId: " + postId + " and commentId: " + commentId
            );
        }

        commentClient.deleteByPostIdAndCommentId(postId, commentId);
    }
}
