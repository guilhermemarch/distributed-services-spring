package com.pbcompass.microserviceA.mapper;

import com.pbcompass.microserviceA.dto.CommentDTO;
import com.pbcompass.microserviceA.dto.UpdateCommentDTO;
import com.pbcompass.microserviceA.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    CommentDTO toDTO(Comment comment);

    Comment toComment(CommentDTO dto);

    CommentDTO UpdatetoComment(UpdateCommentDTO dto);

    UpdateCommentDTO toUpdateToComment(Comment comment);

    UpdateCommentDTO UpdateCommentToDTO(CommentDTO commentdto);
}
