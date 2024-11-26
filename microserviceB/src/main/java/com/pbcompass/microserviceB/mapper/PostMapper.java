package com.pbcompass.microserviceB.mapper;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import org.mapstruct.Mapper;

@Mapper
public interface PostMapper {

    PostDTO toDTO(Post post);

    Post toPost(PostDTO dto);
}
