package com.pbcompass.microserviceB.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {

    @NotBlank
    private Long userId;

    @Field("postId")
    @NotBlank
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

}
