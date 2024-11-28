package com.pbcompass.microserviceB.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {

    @NotNull
    private Long userId;

    @Field("postId")
    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    public PostDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }


}
