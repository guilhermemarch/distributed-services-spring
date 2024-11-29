package com.pbcompass.microserviceB.service;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import com.pbcompass.microserviceB.feign.PostClient;
import com.pbcompass.microserviceB.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostClient postClient;

    public Post findById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }

    public void delete(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (!post.isPresent()) {
            throw new ObjectNotFoundException("No posts found with the id " + id);
        }
        postRepository.deleteById(id);
    }

    public Post create(Post post) {
        Post lastPost = postRepository.findTopByOrderByDocumentIdDesc();
        if (lastPost == null) {
            post.setId(1L);
        } else {
            Long nextId = lastPost.getId() + 1;
            post.setId(nextId);
        }
        return postRepository.save(post);
    }

    public Post update(Long id, Post post) {
        Post postUpd = findById(id);
        postUpd.getUserId();
        postUpd.getId();
        postUpd.setTitle(post.getTitle());
        postUpd.setBody(post.getBody());
        return postRepository.save(postUpd);
    }

    public List<PostDTO> findPostsJsonPlaceholder() {
        return postClient.getPosts();
    }

    public List<Post> findAll() {
        List<Post> posts = postRepository.findAll();
        if (posts.isEmpty()) {
            throw new ObjectNotFoundException("No posts found");
        }
        return posts;
    }

}
