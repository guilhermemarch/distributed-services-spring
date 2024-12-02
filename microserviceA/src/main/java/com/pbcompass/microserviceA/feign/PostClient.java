package com.pbcompass.microserviceA.feign;

import com.pbcompass.microserviceA.entity.Comment;
import com.pbcompass.microserviceA.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "microservicob", url = "http://localhost:8081/api")
public interface PostClient {

    @GetMapping("/posts/allPosts")
    List<PostDTO> fetchAllPosts();

    @GetMapping("/posts/{id}")
    PostDTO fetchPostById(@PathVariable Long id);

    @GetMapping("/posts/{id}")
    Optional<Post> fetchOptionalPostById(@PathVariable Long id);

    @PostMapping("/posts")
    Post createPost(Post post);

    @PutMapping("/posts/{id}")
    PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postdto);

    @GetMapping("/posts/{postId}/comments")
    List<CommentDTO> fetchCommentsByPostId(@PathVariable String postId);

    @GetMapping("/posts/{id}")
    Optional<PostDTO> fetchByPostID(@PathVariable Long id);

    @DeleteMapping("/posts/{id}")
    void deleteById(@PathVariable Long id);

    @GetMapping("/posts/{commentId}")
    Optional<Comment> fetchOptionalCommentId(@PathVariable Long commentId);

    @GetMapping("/posts/{commentId}")
    Optional<CommentDTO> fetchOptionalCommentDTOId(@PathVariable Long commentId);

    @GetMapping("/posts/{postId}/{commentId}")
    CommentDTO fetchCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);

    @PutMapping("/posts/{postId}/{commentId}")
    Comment updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment);

    @DeleteMapping("/posts/{postId}/{commentId}")
    void deleteByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);

    @GetMapping("/posts/{postId}/comments")
    List<CommentDTO> fetchCommentsByPostId(@PathVariable Long postId);

    @PostMapping("/posts/{postId}/comments")
    Comment createComment(@PathVariable Long postId, @RequestBody Comment comment);

}