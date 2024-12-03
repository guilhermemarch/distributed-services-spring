package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.feign.PostClient;
import com.pbcompass.microserviceB.entity.Comment;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostClient postClient;

    public List<PostDTO> findAllPosts() {
        return postClient.findAllPosts();
    }

    public PostDTO findPostById(Long id) {
        Optional<PostDTO> post = postClient.findByPostId(id);
        return post.orElseThrow(() -> new ObjectNotFoundException("Post not found"));
    }

    public Post createPost(Post post) {
        return postClient.createPost(post);
    }

    public PostDTO updatePost(Long id, PostDTO postdto) {
        PostDTO postUpd = postClient.findByPostId(id)
                .orElseThrow(() -> new ObjectNotFoundException("No post found with the id " + id));

        postUpd.getUserId();
        postUpd.getId();
        postUpd.setTitle(postdto.getTitle());
        postUpd.setBody(postdto.getBody());
        return postClient.updatePost(id, postUpd);
    }

    public void deleteByPostID(Long id) {
        Optional<PostDTO> post = postClient.findByPostId(id);
        if (post.isEmpty()) {
            throw new ObjectNotFoundException("No posts found with the id: " + id);
        }

        postClient.deleteById(id);
    }
}



