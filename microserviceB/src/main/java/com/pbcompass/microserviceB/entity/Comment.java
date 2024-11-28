package com.pbcompass.microserviceB.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comment")
public class Comment implements Serializable {

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
        Comment comments = (Comment) o;
        return Objects.equals(documentId, comments.documentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId);
    }
}
