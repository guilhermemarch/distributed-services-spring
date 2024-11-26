package com.pbcompass.microserviceB.controller;

import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    PostService postService;



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

}
