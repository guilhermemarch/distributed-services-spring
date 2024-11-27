package com.pbcompass.microserviceB.mapper;

import com.pbcompass.microserviceB.dto.PostDTO;
import com.pbcompass.microserviceB.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper( PostMapper.class );
    PostDTO toDTO(Post post);

    Post toPost(PostDTO dto);
}
