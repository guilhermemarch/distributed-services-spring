package com.pbcompass.microserviceB.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDTO implements Serializable {

    private String documentId;
    private Long id;
    private Long userId;
    private String title;
    private String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDTO postDTO = (PostDTO) o;
        return Objects.equals(documentId, postDTO.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(documentId);
    }
}
