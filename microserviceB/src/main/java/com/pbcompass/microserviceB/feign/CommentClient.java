package com.pbcompass.microserviceB.feign;

import com.pbcompass.microserviceB.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
@FeignClient(value = "CommentClient", url = "https://jsonplaceholder.typicode.com/")
public interface CommentClient {

    @GetMapping(value = "/comments")
    List<CommentDTO> getComments();

}
