package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.dto.UpdatePostDTO;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
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



    //Matheus: CRIAR EXCEPTION CASO TENTE CRIAR UM POST COM UM postID já existente
    @Test
    public void createPost_WithValidData_ReturnsStatus201() {
        PostDTO responseBody = testClient
                .post()
                .uri("/posts")
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
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO("TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/posts");

        testClient
                .post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(1L, 1L ,"", ""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("status").isEqualTo(400)
                .jsonPath("method").isEqualTo("POST")
                .jsonPath("path").isEqualTo("/posts");
    }

    @Test
    public void updatePost_WithValidData_ReturnsStatus200() {
        PostDTO responseBody = testClient
                .put()
                .uri("/posts/67477aa2f2926210290d5143")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UpdatePostDTO(4L, 3L, "TITLE TEST", "BODY TEST"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(1L);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUserId()).isEqualTo(1L);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTitle()).isEqualTo("TITLE TEST");
        org.assertj.core.api.Assertions.assertThat(responseBody.getBody()).isEqualTo("BODY TEST");
    }

    @Test
    public void findAll_WithoutPosts_ThrowsNoPostsFoundException() {
        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();

        assertThatThrownBy(() -> postService.findAll())
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("No posts found");
    }

    @Test
    public void findAll_WithPosts_ReturnsPostList() {
        postRepository.deleteAll();

        PostDTO responseBody = testClient
                .post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PostDTO(7L, 1L, "corsabrancoturbinado", "BODY TEST"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(PostDTO.class)
                .returnResult().getResponseBody();

        List<Post> posts = postService.findAll();
        assertThat(posts).isNotEmpty();
        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("corsabrancoturbinado");
    }

    @Test
    public void findPostsJsonPlaceholder_ReturnStatus200() {
        List<PostDTO> responseBody = testClient
                .get()
                .uri("/posts/syncData")
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
                .uri("/posts/syncData")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        postRepository.deleteAll();
    }

    @Test
    public void deletePost_WithManualId_ReturnsStatus204() {
        String manualId = "67460d5007e812415cbe0754";
        boolean postExistsBeforeDelete = postRepository.findById(manualId).isPresent();
        assertThat(postExistsBeforeDelete).isTrue();
        testClient
                .delete()
                .uri("/posts/" + manualId)
                .exchange()
                .expectStatus().isNoContent();
        boolean postExistsAfterDelete = postRepository.findById(manualId).isPresent();
        assertThat(postExistsAfterDelete).isFalse();
    }

    @Test
    public void deletePost_WithInvalidId_ReturnsStatus404() {
        String invalidId = "67460d5007e812415cbe0759";
        boolean postExists = postRepository.findById(invalidId).isPresent();
        assertThat(postExists).isFalse();
        testClient
                .delete()
                .uri("/posts/{id}", invalidId)
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
                .uri("/posts/" + invalidId)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.status").isEqualTo(404)
                .jsonPath("$.error").isEqualTo("Not Found");  // Corrigido para "Not Found"
    }

    @Test
    public void findById_PostFound_ReturnsStatus200() {
        // Supondo que o ID do post a ser encontrado seja "67460d5007e812415cbe0754"
        String validId = "67460d5007e812415cbe0753";

        // Certificando-se de que o post com esse ID existe
        Post post = postRepository.findById(validId).orElseThrow(() -> new RuntimeException("Post not found"));

        // Realizando a requisição GET para buscar o post pelo ID
        PostDTO responseBody = testClient
                .get()
                .uri("/posts/" + validId)
                .exchange()
                .expectStatus().isOk()  // Espera o status 200 (OK)
                .expectBody(PostDTO.class)  // Espera o corpo da resposta como um PostDTO
                .returnResult().getResponseBody();

    }

}

