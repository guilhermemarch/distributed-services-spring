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

    @GetMapping("/posts/allposts")
    List<PostDTO> fetchAllPosts();

    @GetMapping("/posts/{id}")
    Optional<Post> fetchPostById(@PathVariable Long id);

    @PostMapping("/posts")
    Post createPost(Post post);

    @PutMapping("/posts/{id}")
    PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postdto);

    @GetMapping("/posts/{postId}/comments")
    List<CommentDTO> fetchCommentsByPostId(@PathVariable String postId);

    @GetMapping("/{id}")
    Optional<PostDTO> fetchByPostID(@PathVariable("id") Long id);

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable("id") Long id);

    @GetMapping("/posts/{commentId}")
    Optional<Comment> fetchOptionalCommentId(@PathVariable Long commentId);

    @GetMapping("/posts/{postId}/{commentId}")
    CommentDTO fetchCommentByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);

    @PutMapping("/posts/{postId}/{commentId}")
    CommentDTO updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO);

    @DeleteMapping("/posts/{postId}/{commentId}")
    void deleteByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);

    @DeleteMapping("/posts/{commentId}")
    void deleteCommentById(@PathVariable Long commendId);
    @GetMapping("/posts/{postId}/comments")

    List<CommentDTO> fetchCommentsByPostId(@PathVariable long postId);

    @PostMapping("/posts/{id}/comments")
    Comment createComment(@PathVariable("id") Long postId, @RequestBody Comment comment);

}