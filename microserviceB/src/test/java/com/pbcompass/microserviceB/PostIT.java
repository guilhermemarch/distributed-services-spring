package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    PostRepository postRepository;

    @Test
    public void createPost_WithValidData_ReturnsStatus201(){
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
    public void findPostsJsonPlaceholder_ReturnStatus200(){
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
    public void syncData_ReturnStatus201(){
        List<PostDTO> responseBody = testClient
                .post()
                .uri("/posts/syncData")
                .exchange()
                .expectStatus().isCreated()
                .expectBodyList(PostDTO.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();;
        postRepository.deleteAll();
    }



}
