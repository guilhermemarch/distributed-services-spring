package com.pbcompass.microserviceA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CommentDTO {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class postDTO {
        private long id;
        private String title;
        private String body;
        private String postId;
    }


}
