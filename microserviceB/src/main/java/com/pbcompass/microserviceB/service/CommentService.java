package com.pbcompass.microserviceB.service;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.entity.Comment;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.feign.CommentClient;
import com.pbcompass.microserviceB.repository.CommentRepository;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private PostRepository postRepository;

    public Comment createFromJsonPlaceHolder(Comment comment) {
        Comment lastComment = commentRepository.findTopByOrderByDocumentIdDesc();
        if (lastComment == null) {
            comment.setId(1L);
        } else {
            Long nextId = lastComment.getId() + 1;
            comment.setId(nextId);
        }

        Comment commentCreated = commentRepository.save(comment);

        Optional<Post> optionalPost = postRepository.findById(comment.getPostId());
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            if (post.getId() == comment.getPostId()) {
                post.getComments().addAll(Arrays.asList(comment));
            }

            postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with ID: " + comment.getId());
        }

        return commentCreated;
    }

    public Comment createComment(Long id, Comment comment) {
        Comment lastComment = commentRepository.findTopByOrderByDocumentIdDesc();
        if (lastComment == null) {
            comment.setId(1L);
        } else {
            Long nextId = lastComment.getId() + 1;
            comment.setId(nextId);
        }

        Comment commentCreated;

        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            comment.setPostId(id);
            commentCreated = commentRepository.save(comment);
            Post post = optionalPost.get();

            if (post.getId().equals(id)) {
                post.getComments().addAll(Arrays.asList(comment));
            }

            postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with ID: " + comment.getId());
        }

        return commentCreated;

    }

    public List<Comment> findAll(Long id) {
        List<Comment> comments = postRepository.findById(id).get().getComments();

        if (comments.isEmpty()) {
            throw new ObjectNotFoundException("No posts found");
        }

        return comments;
    }

    public Comment update(Long id, Long commentId, Comment comment) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Post not found"));

        Comment commentUpd = commentRepository.findById(commentId)
                .filter(c -> c.getPostId().equals(post.getId()))
                .orElseThrow(() -> new ObjectNotFoundException("Comment not found"));

        commentUpd.getPostId();
        commentUpd.getId();
        commentUpd.setName(comment.getName());
        commentUpd.setEmail(comment.getEmail());
        commentUpd.setBody(comment.getBody());
        return commentRepository.save(commentUpd);
    }

    public void delete(Long id, Long commentId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + id));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ObjectNotFoundException("No comment found with the id " + commentId));

        if (!post.getComments().contains(comment)) {
            throw new ObjectNotFoundException("No comment found with the id " + commentId + " in post with id " + id);
        }

        post.getComments().remove(comment);
        postRepository.save(post);
        commentRepository.deleteById(commentId);
    }

    public List<CommentDTO> findCommentsJsonPlaceholder() {
        return commentClient.getComments();
    }


}
