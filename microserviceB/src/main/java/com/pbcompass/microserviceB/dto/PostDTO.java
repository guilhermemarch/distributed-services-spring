package com.pbcompass.microserviceB.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {

    private Long userId;
    @Field("postId")
    private Long id;
    private String title;
    private String body;

}
