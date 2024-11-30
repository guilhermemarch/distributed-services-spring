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

/*    public Optional<CommentDTO> fetchOptionalCommentByPostIdAndCommentId(Long postId, Long commentId) {
        return commentClient.fetchOptionalCommentId(commentId);
    }*/

    public CommentDTO updatePost(Long postId, Long commentId, CommentDTO commentdto) {
        CommentDTO commentUpd = fetchCommentByPostIdAndCommentId(postId, commentId);
        commentUpd.getPostId();
        commentUpd.getId();
        commentUpd.setName(commentdto.getName());
        commentUpd.setEmail(commentdto.getEmail());
        commentUpd.setBody(commentdto.getEmail());
        return commentClient.updateComment(postId, commentId, commentUpd);
    }

    public List<CommentDTO> fetchCommentsByPostId(long postId) {
        return commentClient.fetchCommentsByPostId(postId);
    }

    public Comment createComment(Long postId, Comment comment) {
        return commentClient.createComment(postId, comment);
    }


    public void deleteComment(Long postId, Long commentId) {
        Post post = commentClient.fetchPostById(postId)
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
