package com.pbcompass.microserviceB.feign;

import com.pbcompass.microserviceB.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@FeignClient(value = "PostClient", url = "https://jsonplaceholder.typicode.com/")
public interface PostClient {
    @GetMapping(value = "/posts")
    List<PostDTO> getPosts();
}
