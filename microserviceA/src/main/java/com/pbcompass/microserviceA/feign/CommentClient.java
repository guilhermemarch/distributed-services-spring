package com.pbcompass.microserviceA.feign;

import com.pbcompass.microserviceA.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "microservicoBComment", url = "http://localhost:8081")
public interface CommentClient {

    @GetMapping("/posts/{postId}/{commentId}")
    Optional<CommentDTO> fetchOptionalCommentByPostIdAndCommentId(@PathVariable Long id, @PathVariable Long commentId);

    @GetMapping("/posts/{postId}/{commentId}")
    CommentDTO fetchCommentByPostIdAndCommentId(@PathVariable Long id, @PathVariable Long commentId);

    @PutMapping("/posts/{postId}/{commentId}")
    CommentDTO updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentDTO commentDTO);

    @DeleteMapping("/posts/{postId}/{commentId}")
    void deleteByPostIdAndCommentId(@PathVariable Long postId, @PathVariable Long commentId);
}
