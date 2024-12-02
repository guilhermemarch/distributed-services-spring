package com.pbcompass.microserviceA.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostDTO implements Serializable {

    @NotNull
    private Long userId;

    @Field("postId")
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    @DBRef(lazy = true)
    private List<CommentDTO> comments = new ArrayList<>();

}
