package com.pbcompass.microserviceA.mapper;

import com.pbcompass.microserviceA.dto.PostDTO;
import com.pbcompass.microserviceA.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper( PostMapper.class );
    PostDTO toDTO(Post post);
    Post toPost(PostDTO dto);
}
