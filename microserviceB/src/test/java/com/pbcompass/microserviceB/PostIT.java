package com.pbcompass.microserviceB;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.service.exception.NoPostsFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.PostService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

    @Autowired
    WebTestClient testClient;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;


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



}
