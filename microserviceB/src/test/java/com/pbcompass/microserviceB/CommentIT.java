package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.repository.CommentRepository;
import com.pbcompass.microserviceB.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

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

    @Test
    public void updateComment_WithValidData_ReturnsStatus200() {
        CommentDTO updateDTO = new CommentDTO("Jane Doe", "jane.doe@example.com", "Updated comment body");

        CommentDTO responseBody = testClient
                .put()
                .uri("/api/posts/1/3")
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
    public void findAllJsonPlaceholderComments_ReturnsStatus200() {
        List<CommentDTO> responseBody = testClient
                .get()
                .uri("/api/posts/syncDataComments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDTO.class)
                .returnResult().getResponseBody();

        assertThat(responseBody).isNotNull();
        assertThat(responseBody).isNotEmpty();
    }

    @Test
    public void createComment_WithInvalidData_ReturnsStatus400() {

        testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CommentDTO(1L, "", "missing.email@example.com", ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/posts/1/comments");
    }

    @Test
    public void createComment_WithInvalidData_ReturnsStatus201() {
        CommentDTO responseBody = testClient
                .post()
                .uri("/api/posts/1/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new CommentDTO(1L, "john", "john.doe@example.com", "This is a comment"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CommentDTO.class)
                .returnResult().getResponseBody();


        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getName()).isEqualTo("John Doe");
        assertThat(responseBody.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(responseBody.getBody()).isEqualTo("This is a comment");
        assertThat(responseBody.getPostId()).isEqualTo(1L);
    }

    @Test
    public void findAllComments_ForPost_ReturnsStatus200() {
        testClient
                .get()
                .uri("/api/posts/1/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDTO.class)
                .hasSize(1);
    }

    @Test
    public void findAllComments_ForPostWithNoComments_ReturnsStatus200AndEmptyList() {
        testClient
                .get()
                .uri("/api/posts/1/comments")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDTO.class)
                .hasSize(0);
    }

    @Test
    public void deleteComment_ReturnsStatus204() {
        testClient
                .delete()
                .uri("/api/posts/1/3")
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    public void deleteComment_NotFound_ReturnsStatus404() {
        testClient
                .delete()
                .uri("/api/posts/1/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");
    }
}
