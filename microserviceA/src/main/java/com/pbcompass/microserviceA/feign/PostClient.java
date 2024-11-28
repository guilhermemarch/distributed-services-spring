package com.pbcompass.microserviceA.feign;

import com.pbcompass.microserviceA.entity.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "microservicob", url = "http://localhost:8081")
public interface PostClient {

    @GetMapping("/posts/allposts")
    List<PostDTO> fetchAllPosts();

    @GetMapping("/posts/{id}")
    PostDTO fetchPostById(@PathVariable String id);

    @PostMapping("/posts")
    Post createPost(Post post);

    @GetMapping("/comments/bypost/{postId}")
    List<CommentDTO> fetchCommentsByPostId(@PathVariable String postId);
}