package com.pbcompass.microserviceA.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pbcompass.microserviceA.dto.CommentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

    @Id
    private String documentId;

    private Long postId;

    @Field("commentId")
    private Long id;

    private String name;

    private String email;

    private String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(documentId, comment.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(documentId);
    }
}
