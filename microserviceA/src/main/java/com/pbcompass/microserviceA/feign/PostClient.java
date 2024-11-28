package com.pbcompass.microserviceA.feign;

import com.pbcompass.microserviceA.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;

import java.util.List;

@FeignClient(name = "microservicob", url = "http://localhost:8081")
public interface PostClient {

    @GetMapping("/posts/allposts")
    List<PostDTO> fetchAllPosts();

    @GetMapping("/posts/{id}")
    PostDTO fetchPostById(@PathVariable String id);

    @PostMapping("/posts")
    Post createPost(Post post);

    @PutMapping("/posts/{id}")
    PostDTO updatePost(@PathVariable String id, @RequestBody PostDTO postdto);

    @GetMapping("/posts/{postId}/comments")
    List<CommentDTO> fetchCommentsByPostId(@PathVariable String postId);
}