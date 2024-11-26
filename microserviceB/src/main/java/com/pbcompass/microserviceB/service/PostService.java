package com.pbcompass.microserviceB.service;

import com.pbcompass.microserviceB.entity.Post;
import com.pbcompass.microserviceB.mapper.PostMapper;
import com.pbcompass.microserviceB.repository.PostRepository;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public Post findById(String id) {
        Optional<Post> post = postRepository.findById(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Object not found"));

    }

    public void delete(String id) {
        postRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Object not found"));
        postRepository.deleteById(id);
    }

  
public Post create(Post post){
        return postRepository.save(post);
    }
  
    public Post updatePost(String id, String title, String body) {
        Post post = findById(id);
        post.setTitle(title);
        post.setBody(body);
        return postRepository.save(post);
    }
}
