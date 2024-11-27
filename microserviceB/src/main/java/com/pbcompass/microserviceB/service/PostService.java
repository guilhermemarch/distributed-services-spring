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

    public List<PostDTO> findPostsJsonPlaceholder() {
        return postClient.getPosts();
    }

    public Post create(Post post){
        return postRepository.save(post);
    }
}
