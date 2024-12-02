package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.dto.UpdatePostDTO;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.service.PostService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    @Order(1)
    public void createPost_WithValidData_ReturnsStatus201() {
        PostDTO responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(7L, 1L, "TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUserId()).isEqualTo(7L);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTitle()).isEqualTo("TITLE TEST");
        org.assertj.core.api.Assertions.assertThat(responseBody.getBody()).isEqualTo("BODY TEST");
    }

    @Test
    @Order(2)
    public void createPost_WithInvalidData_ReturnsStatus400() {
        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO("TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/posts");

        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(1L, 1L ,"", ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/api/posts");
    }

    @Test
    @Order(3)
    public void updatePost_WithValidData_ReturnsStatus200() {
        Long postId = 1L;

        boolean postExists = postRepository.findById(postId).isPresent();
        if (!postExists) {
            testClient
                    .post()
                    .uri("/api/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new PostDTO(1L, postId, "Initial Title", "Initial Body"))
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody(PostDTO.class)
                    .returnResult()
                    .getResponseBody();
        }
        UpdatePostDTO updateDTO = new UpdatePostDTO(1L, postId, "Updated Title", "Updated Body");

        PostDTO updatedPost = testClient
                .put()
                .uri("/api/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .returnResult()
                .getResponseBody();
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
        assertThat(updatedPost.getBody()).isEqualTo("Updated Body");
    }

    @Test
    @Order(4)
    public void updatePost_WithInvalidData_ReturnsStatus400() {
        testClient
                .put()
                .uri("/api/posts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePostDTO("", ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("PUT")
                .jsonPath("path").isEqualTo("/api/posts/1");
    }

    @Test
    @Order(5)
    public void updatePost_WithInvalidId_ReturnsStatus404() {
        testClient
                .put()
                .uri("/api/posts/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePostDTO("TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo(404)
                .jsonPath("method").isEqualTo("PUT")
                .jsonPath("path").isEqualTo("/api/posts/999");
    }

    @Test
    @Order(6)
    public void findAll_WithPosts_ReturnsStatus200() {
        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(1L, 1L, "TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        List<Post> posts = postService.findAll();
        assertThat(posts).isNotEmpty();
    }

    @Test
    @Order(7)
    public void findPostsJsonPlaceholder_ReturnStatus200() {
        List<PostDTO> responseBody = testClient
                .get()
                .uri("/api/posts/syncData")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isGreaterThan(1);
    }

    @Test
    @Order(8)
    public void deletePost_WithInvalidId_ReturnsStatus404() {
        boolean postExists = postRepository.findById(999L).isPresent();
        assertThat(postExists).isFalse();
        testClient
                .delete()
                .uri("/api/posts/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.path").exists()
                .jsonPath("$.method").exists();
    }

    @Test
    @Order(9)
    public void findById_PostNotFound_ReturnsStatus404() {
        testClient
                .get()
                .uri("/api/posts/999")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");
    }

    @Test
    @Order(10)
    public void findById_PostFound_ReturnsStatus200() {
        testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(1L, 1L, "TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        testClient
                .get()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();
    }

    @Test
    @Order(11)
    public void deletePost_WithManualId_ReturnsStatus204() {
        boolean postExistsBeforeDelete = postRepository.findById(1L).isPresent();
        assertThat(postExistsBeforeDelete).isTrue();
        testClient
                .delete()
                .uri("/api/posts/1")
                .exchange()
                .expectStatus().isNoContent();
        boolean postExistsAfterDelete = postRepository.findById(1L).isPresent();
        assertThat(postExistsAfterDelete).isFalse();
    }

    @Test
    @Order(12)
    public void syncData_ReturnStatus201() {
        List<PostDTO> responseBody = testClient
                .post()
                .uri("/api/posts/syncData")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        postRepository.deleteAll();
    }

    @Test
    @Order(13)
    public void findAll_WithoutPosts_ReturnsStatus404() {
        postRepository.deleteAll();
        assertThat(postRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> postService.findAll())
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("No posts found");
    }

}

