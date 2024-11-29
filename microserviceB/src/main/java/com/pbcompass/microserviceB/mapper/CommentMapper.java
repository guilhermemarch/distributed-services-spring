package com.pbcompass.microserviceB.mapper;

import com.pbcompass.microserviceB.dto.CommentDTO;
import com.pbcompass.microserviceB.dto.UpdateCommentDTO;
import com.pbcompass.microserviceB.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO toDTO(Comment comment);

    Comment toComment(CommentDTO dto);

    Comment UpdatetoComment(UpdateCommentDTO dto);

    UpdateCommentDTO UpdateCommentToDTO(Comment comment);
}
