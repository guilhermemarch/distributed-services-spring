package com.pbcompass.microserviceA.controller;

import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;
import com.pbcompass.microserviceA.dto.UpdateCommentDTO;
import com.pbcompass.microserviceA.dto.UpdatePostDTO;
import com.pbcompass.microserviceA.mapper.CommentMapper;
import com.pbcompass.microserviceA.mapper.PostMapper;
import com.pbcompass.microserviceA.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private final CommentMapper commentMapper;

    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<UpdateCommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody @Valid UpdateCommentDTO dto) {
        CommentDTO commentDTO = commentService.updatePost(postId, commentId, commentMapper.UpdatetoComment(dto));
        return ResponseEntity.ok(commentMapper.UpdateCommentToDTO(commentDTO));
    }

    @DeleteMapping(value = "/{postId}/{commentId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteByPostID(id, commentId);
        return ResponseEntity.noContent().build();
    }
}
