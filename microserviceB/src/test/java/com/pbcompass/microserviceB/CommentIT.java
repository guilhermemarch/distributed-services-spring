package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.repository.CommentRepository;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.CommentService;
import com.pbcompass.microserviceB.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    private Long commentId;

    @BeforeEach
    public void setup() {

        postId = 1L;
        if (checkIfPostExists(postId) == null) {
            createPost(postId);
        }
        commentId = createComment(postId);
    }

    @Test
    @Order(1)
    public void createComment_WithValidData_ReturnsStatus201() {
        CommentDTO commentDTO = new CommentDTO(postId, "John Doe", "john.doe@example.com", "This is a comment");

        CommentDTO responseBody = testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commentDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CommentDTO.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("John Doe");
        assertThat(responseBody.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(responseBody.getBody()).isEqualTo("This is a comment");
        assertThat(responseBody.getPostId()).isEqualTo(postId);
    }

    @Test
    @Order(2)
    public void updateComment_WithValidData_ReturnsStatus200() {
        CommentDTO updateDTO = new CommentDTO(postId, "Jane Doe", "jane.doe@example.com", "Updated comment body");

        // Atualizando o comentário
        CommentDTO responseBody = testClient
                .put()
                .uri("/api/posts/" + postId + "/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDTO.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("Jane Doe");
        assertThat(responseBody.getEmail()).isEqualTo("jane.doe@example.com");
        assertThat(responseBody.getBody()).isEqualTo("Updated comment body");
    }

    @Test
    @Order(3)
    public void findAllComments_ForPost_ReturnsStatus200() {
        testClient
                .get()
                .uri("/api/posts/1/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDTO.class);
    }

    @Test
    @Order(4)
    public void findCommentById_ValidId_ReturnsStatus200() {
        testClient
                .get()
                .uri("/api/posts/1/comments/" + commentId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CommentDTO.class)
                .consumeWith(response -> {
                    CommentDTO comment = response.getResponseBody();
                    assertThat(comment).isNotNull();
                    assertThat(comment.getPostId()).isEqualTo(1L);
                    assertThat(comment.getId()).isEqualTo(commentId);
                });
    }

    @Test
    @Order(5)
    public void findCommentById_InvalidId_ReturnsStatus404() {
        testClient
                .get()
                .uri("/api/posts/1/comments/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    @Order(6)
    public void deleteComment_ReturnsStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1/1")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(7)
    public void deleteComment_NotFound_ReturnsStatus404() {
        testClient
                .delete()
                .uri("/api/posts/1/comments/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    @Order(8)
    public void createComment_WithInvalidData_ReturnsStatus400() {
        CommentDTO invalidComment = new CommentDTO(postId, "", "missing.email@example.com", "");

        testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidComment)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/posts/1/comments");
    }

    private Long checkIfPostExists(Long postId) {
        return postRepository.findById(postId).map(post -> post.getId()).orElse(null);
    }

    private void createPost(Long postId) {
        PostDTO postDTO = new PostDTO(postId, "TITLE TEST", "BODY TEST");
        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(postDTO)
                .exchange()
                .expectStatus().isCreated();
    }

    private Long createComment(Long postId) {
        CommentDTO commentDTO = new CommentDTO(postId, "Initial Author", "author@example.com", "This is a comment");
        return testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(commentDTO)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CommentDTO.class)
                .returnResult()
                .getResponseBody()
                .getId();
    }
}
