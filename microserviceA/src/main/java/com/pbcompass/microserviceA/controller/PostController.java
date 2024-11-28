package com.pbcompass.microserviceA.controller;

import com.pbcompass.microserviceA.dto.UpdatePostDTO;
import com.pbcompass.microserviceA.service.PostService;
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
@RequestMapping("/posts")
public class PostController {

        @Autowired
        private PostService postService;

        private final PostMapper postMapper;

        public PostController(PostMapper postMapper) {
                this.postMapper = postMapper;
        }

        @GetMapping("/allposts")
        public ResponseEntity<List<PostDTO>> getAllPosts() {
                return ResponseEntity.ok(postService.fetchAllPosts());
        }

        @GetMapping("/{id}")
        public PostDTO getPostById(@PathVariable String id) {
                return postService.fetchPostById(id);
        }

        @PostMapping
        public ResponseEntity<PostDTO> createPost(@RequestBody @Valid PostDTO dto) {
                Post post = postService.createPost(postMapper.toPost(dto));
                return ResponseEntity.status(HttpStatus.CREATED).body(postMapper.toDTO(post));
        }

        @PutMapping("/{id}")
        public ResponseEntity<UpdatePostDTO> updatePost(@PathVariable String id, @RequestBody @Valid UpdatePostDTO dto) {
                PostDTO post = postService.updatePost(id, postMapper.UpdatetoPost(dto));
                return ResponseEntity.ok(postMapper.UpdatePostToDTO(post));
        }

        @GetMapping("/{postId}/comments")
        public List<CommentDTO> getCommentsByPostId(@PathVariable String postId) {
                return postService.fetchCommentsByPostId(postId);
        }

        //FAZER:
        // DELETE deletepostbyID

        //bonus:
        // Get posts/{postid}/comments
        // POST posts/{postid}/comments
        // PUT posts/{postID}
        // DELETE posts/{POSTID}/{commentID}
}