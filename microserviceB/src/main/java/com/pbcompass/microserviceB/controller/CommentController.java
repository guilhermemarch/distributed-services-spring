package com.pbcompass.microserviceB.controller;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.dto.UpdateCommentDTO;
import com.pbcompass.microserviceB.entity.Comment;
import com.pbcompass.microserviceB.mapper.CommentMapper;
import com.pbcompass.microserviceB.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/posts")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private CommentMapper commentMapper;

    public CommentController(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @GetMapping("/syncDataComments")
    public ResponseEntity<List<CommentDTO>> findAllJsonPlaceholderComments() {
        List<CommentDTO> comments = commentService.findCommentsJsonPlaceholder();

        return ResponseEntity.ok(comments);
    }

    @PostMapping("/syncDataComments")
    public ResponseEntity<List<CommentDTO>> syncDataComments() {
        List<CommentDTO> commentsFromPlaceholder = commentService.findCommentsJsonPlaceholder();
        List<CommentDTO> createdComments = new ArrayList<>();

        for (CommentDTO comment : commentsFromPlaceholder) {
            Comment newComment = commentService.createFromJsonPlaceHolder(commentMapper.toComment(comment));
            createdComments.add(commentMapper.toDTO(newComment));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(createdComments);
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable Long id, @RequestBody @Valid CommentDTO dto) {
        Comment comment = commentService.createComment(id, commentMapper.toComment(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentMapper.toDTO(comment));
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDTO>> findAllComments(@PathVariable Long id) {
        List<Comment> comments = commentService.findAll(id);
        List<CommentDTO> commentDTOs = comments.stream().map(CommentMapper.INSTANCE::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(commentDTOs);
    }

    @DeleteMapping(value = "/{id}/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.delete(id, commentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{commentId}")
    public ResponseEntity<UpdateCommentDTO> updateComment(@PathVariable Long id, @PathVariable Long commentId, @RequestBody @Valid UpdateCommentDTO dto) {
        Comment comment = commentService.update(id, commentId, commentMapper.UpdatetoComment(dto));
        return ResponseEntity.ok(commentMapper.UpdateCommentToDTO(comment));
    }
}
