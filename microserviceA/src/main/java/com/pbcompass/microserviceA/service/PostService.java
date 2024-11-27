package com.pbcompass.microserviceA.service;

import com.pbcompass.microserviceA.entity.Post;
import com.pbcompass.microserviceA.feign.PostClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.PostDTO;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostClient postClient;

    public List<PostDTO> fetchAllPosts() {
        return postClient.fetchAllPosts();
    }

   // public PostDTO createPost(PostDTO postDTO) {
    //    return postClient.createPost(postDTO);
   // }



    public PostDTO fetchPostById(String id) {
        return postClient.fetchPostById(id);
    }

    public List<CommentDTO> fetchCommentsByPostId(String postId) {
        return postClient.fetchCommentsByPostId(postId);
    }
}



