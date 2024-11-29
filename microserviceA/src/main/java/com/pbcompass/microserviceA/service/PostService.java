package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.feign.PostClient;
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

    public List<PostDTO> fetchAllPosts() {
        return postClient.fetchAllPosts();
    }

    public Post createPost(Post post) {
        return postClient.createPost(post);
    }

    public PostDTO updatePost(String id, PostDTO postdto) {
        PostDTO postUpd = fetchPostById(id);
        postUpd.getUserId();
        postUpd.getId();
        postUpd.setTitle(postdto.getTitle());
        postUpd.setBody(postdto.getBody());
        return postClient.updatePost(id, postUpd);
    }

    public PostDTO fetchPostById(String id) {
        return postClient.fetchPostById(id);
    }

    public List<CommentDTO> fetchCommentsByPostId(String postId) {
        return postClient.fetchCommentsByPostId(postId);
    }



    public void deleteByPostID(Long id) {
        Optional<PostDTO> post = postClient.fetchByPostID(id);
        if (!post.isPresent()) {
            throw new ObjectNotFoundException("No posts found with the id: " + id);
        }

        postClient.deleteById(id);
    }


}



