package com.pbcompass.microserviceB.controller;
import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.mapper.PostMapper;
import com.pbcompass.microserviceB.service.PostService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<PostDTO> findById(@PathVariable String id) {
        Post post = postService.findById(id);
        return ResponseEntity.ok().body(postMapper.toDTO(post));
    }
   

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO dto) {
        Post post = postService.create(postMapper.toPost(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDTO(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable String id, @RequestBody PostDTO dto) {
        Post post = postService.update(id, postMapper.toPost(dto));
        return ResponseEntity.ok(postMapper.toDTO(post));
    }
  
    @GetMapping("/syncData")
    public ResponseEntity<List<PostDTO>> findAllJsonPlaceholder() {
        List<PostDTO> posts = postService.findPostsJsonPlaceholder();

        return ResponseEntity.ok(posts);
    }

    @PostMapping("/syncData")
    public ResponseEntity<List<PostDTO>> syncData() {
        List<PostDTO> postsFromPlaceholder = postService.findPostsJsonPlaceholder();
        List<PostDTO> createdPosts = new ArrayList<>();

        for (PostDTO post : postsFromPlaceholder) {
            Post newPost = postService.create(postMapper.toPost(post));
            createdPosts.add(postMapper.toDTO(newPost));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPosts);
    }

    @GetMapping(value = "/allposts")
    public ResponseEntity<List<Post>> findAll() {
        List<Post> obj = postService.findAll();
        return ResponseEntity.ok().body(obj);
    }
}
