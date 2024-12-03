package com.pbcompass.microserviceA.feign;

import com.pbcompass.microserviceA.dto.UpdateCommentDTO;
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
    List<PostDTO> findAllPosts();

    @GetMapping("/posts/{id}")
    Optional<PostDTO> findByPostId(@PathVariable Long id);

    @PostMapping("/posts")
    Post createPost(Post post);

    @PutMapping("/posts/{id}")
    PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postDto);

    @DeleteMapping("/posts/{id}")
    void deleteById(@PathVariable Long id);


//----------------------------------------------------------------------------


    @GetMapping("/posts/comment/{commentId}")
    Optional<CommentDTO> findByCommentId(@PathVariable Long commentId);

    @GetMapping("/posts/{postId}/comments")
    List<CommentDTO> findCommentsByPostId(@PathVariable Long postId);

    @PostMapping("/posts/{postId}/comments")
    CommentDTO createComment(@PathVariable Long postId, @RequestBody CommentDTO comment);

    @PutMapping("/posts/{postId}/{commentId}")
    CommentDTO updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDTO commentDto);

    @DeleteMapping("/posts/{postId}/{commentId}")
    void deleteComment(@PathVariable Long postId, @PathVariable Long commentId);

}