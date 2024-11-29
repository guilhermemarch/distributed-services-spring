package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.dto.UpdatePostDTO;
import com.pbcompass.microserviceB.service.exception.NoPostsFoundException;
import com.pbcompass.microserviceB.repository.PostRepository;
import org.junit.jupiter.api.Test;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
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
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1L);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUserId()).isEqualTo(7L);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTitle()).isEqualTo("TITLE TEST");
        org.assertj.core.api.Assertions.assertThat(responseBody.getBody()).isEqualTo("BODY TEST");
    }

    @Test
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
    public void updatePost_WithValidData_ReturnsStatus200() {
        String postId = "67460d5007e812415cbe0753";  // ID válido, adaptado para String

        // Altere os valores de title e body conforme necessário
        UpdatePostDTO updateDTO = new UpdatePostDTO(1L, 2L, "Novo Título 2", "Novo conteúdo de corpo");

        PostDTO responseBody = testClient
                .put()
                .uri("/api/posts/" + postId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        // Valide se a resposta tem os dados atualizados corretamente
        assertThat(responseBody).isNotNull();
        assertThat(responseBody.getTitle()).isEqualTo("Novo Título 2");
        assertThat(responseBody.getBody()).isEqualTo("Novo conteúdo de corpo");
        assertThat(responseBody.getUserId()).isEqualTo(1L);
    }



    @Test
    public void findAll_WithoutPosts_ThrowsNoPostsFoundException() {
        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> postService.findAll())
                .isInstanceOf(NoPostsFoundException.class)
                .hasMessage("No posts found");
    }

    @Test
    public void findAll_WithPosts_ReturnsPostList() {
        postRepository.deleteAll();

        PostDTO responseBody = testClient
                .post()
                .uri("/api/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(7L, 1L, "test_unit", "BODY TEST"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        List<Post> posts = postService.findAll();
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("test_unit");
    }

    @Test
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
    public void deletePost_WithManualId_ReturnsStatus204() {
        String validId = "67460d5007e812415cbe0754";
        boolean postExistsBeforeDelete = postRepository.findById(validId).isPresent();
        assertThat(postExistsBeforeDelete).isTrue();
        testClient
                .delete()
                .uri("/api/posts/" + validId)
                .exchange()
                .expectStatus().isNoContent();
        boolean postExistsAfterDelete = postRepository.findById(validId).isPresent();
        assertThat(postExistsAfterDelete).isFalse();
    }

    @Test
    public void deletePost_WithInvalidId_ReturnsStatus404() {
        String invalidId = "67460d5007e812415cbe0759";
        boolean postExists = postRepository.findById(invalidId).isPresent();
        assertThat(postExists).isFalse();
        testClient
                .delete()
                .uri("/api/posts/"+ invalidId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("No posts Found")
                .jsonPath("$.path").exists()
                .jsonPath("$.method").exists();
    }

    @Test
    public void findById_PostNotFound_ReturnsStatus404() {
        String invalidId = "non-existing-id";
        testClient
                .get()
                .uri("/api/posts/document/" + invalidId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");  // Corrigido para "Not Found"
    }

    @Test
    public void findById_PostFound_ReturnsStatus200() {
        String validId = "67460d5007e812415cbe0753";

        Post post = postRepository.findById(validId).orElseThrow(() -> new RuntimeException("Post not found"));


        PostDTO responseBody = testClient
                .get()
                .uri("/api/posts/document/" + validId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

    }

}

