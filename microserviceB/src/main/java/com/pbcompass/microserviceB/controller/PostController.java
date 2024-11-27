package com.pbcompass.microserviceB.controller;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.mapper.PostMapper;

import com.pbcompass.microserviceB.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    PostService postService;

    private final PostMapper postMapper;

    public PostController(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        Post obj = postService.findById(id);
        return ResponseEntity.ok().body(obj);

    }

    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable String id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
  
  @PostMapping
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO dto){
        Post post = postService.create(postMapper.toPost(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDTO(post));
    }
  
   @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody PostDTO postDTO) {
        Post updatedPost = postService.updatePost(id, postDTO.getTitle(), postDTO.getBody());
        return ResponseEntity.ok(updatedPost);
    }

    @GetMapping(value = "/allposts")
    public ResponseEntity<List<Post>> findAll() {
        List<Post> obj = postService.findAll();
        return ResponseEntity.ok().body(obj);
    }


}
