package com.pbcompass.microserviceB.controller;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.mapper.PostMapper;
import com.pbcompass.microserviceB.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostService postService;

    private final PostMapper postMapper;

    public PostController(PostMapper postMapper) {
        this.postMapper = postMapper;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = postService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping("/syncData")
    public ResponseEntity<List<PostDTO>> findAllJsonPlaceholder() {
        List<PostDTO> posts = postService.findPostsJsonPlaceholder();

        return ResponseEntity.ok(posts);
    }

    @PostMapping
    public ResponseEntity<List<PostDTO>> syncData() {
        List<PostDTO> postsFromPlaceholder = postService.findPostsJsonPlaceholder();
        List<PostDTO> createdPosts = new ArrayList<>();

        for (PostDTO post : postsFromPlaceholder) {
            Post newPost = postService.create(postMapper.toPost(post));
            createdPosts.add(postMapper.toDTO(newPost));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPosts);
    }


}
