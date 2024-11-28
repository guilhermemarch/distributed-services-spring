package com.pbcompass.microserviceA.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;
import java.util.Objects;


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Document
    public class Post implements Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        private String documentId;
        private Long postId;
        private Long userId;
        private String title;
        private String body;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Post post = (Post) o;
            return Objects.equals(documentId, post.documentId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(documentId);
        }
    }
