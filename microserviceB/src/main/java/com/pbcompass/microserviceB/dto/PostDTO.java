package com.pbcompass.microserviceB.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {

    private Long postId;
    private Long userId;
    private String title;
    private String body;

}
