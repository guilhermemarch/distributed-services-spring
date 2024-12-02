package com.pbcompass.microserviceA.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePostDTO implements Serializable {

    private Long userId;

    @Field("postId")
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String body;

}
