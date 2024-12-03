package com.pbcompass.microserviceA.controller;

import com.pbcompass.microserviceA.dto.UpdateCommentDTO;
import com.pbcompass.microserviceA.dto.UpdatePostDTO;
import com.pbcompass.microserviceA.mapper.CommentMapper;
import com.pbcompass.microserviceA.service.CommentService;
import com.pbcompass.microserviceA.service.PostService;
import com.pbcompass.microserviceB.entity.Comment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;
import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.mapper.PostMapper;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://editor.swagger.io")
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    private final PostMapper postMapper;

    private final CommentMapper commentMapper;

    public PostController(PostMapper postMapper, CommentMapper commentMapper) {
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/allPosts")
    public ResponseEntity<List<PostDTO>> getAllPosts() {
        return ResponseEntity.ok(postService.findAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id) {
        PostDTO post = postService.findPostById(id);
        return ResponseEntity.ok().body(post);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO dto) {
        Post post = postService.createPost(postMapper.toPost(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDTO(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePostDTO> updatePost(@PathVariable Long id, @RequestBody @Valid UpdatePostDTO dto) {
        PostDTO post = postService.updatePost(id, postMapper.UpdatetoPost(dto));
        return ResponseEntity.ok(postMapper.UpdatePostToDTO(post));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable Long id) {
        postService.deleteByPostID(id);
        return ResponseEntity.noContent().build();
    }

    // -----------------------------------------------------------------------------------------------------------------

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.findCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long commentId) {
        CommentDTO comments = commentService.findCommentById(commentId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{postId}/comments") 
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") Long postId, @RequestBody CommentDTO dto) {
        CommentDTO createdComment = commentService.createComment(postId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<UpdateCommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid UpdateCommentDTO dto) {
        CommentDTO comment = commentService.updateComment(postId, commentId, commentMapper.UpdateToComment(dto));
        return ResponseEntity.ok(commentMapper.UpdateToDto(comment));
    }

    @DeleteMapping(value = "/{postId}/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}