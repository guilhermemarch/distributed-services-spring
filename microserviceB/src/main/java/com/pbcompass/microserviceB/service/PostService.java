package com.pbcompass.microserviceB.service;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.feign.PostClient;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
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

    public Post findById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
    }
  
    public void delete(String id) {
        postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found"));
        postRepository.deleteById(id);
    }

    public Post create(Post post) {
        return postRepository.save(post);
    }

    public Post update(String id, Post post) {
        Post postUpd = findById(id);
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
