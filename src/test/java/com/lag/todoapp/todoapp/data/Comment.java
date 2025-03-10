package com.lag.todoapp.todoapp.data;

import com.lag.todoapp.todoapp.entity.CommentEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.model.response.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class Comment {
    public static Page<CommentEntity> getCommentsPageable() {
        return new PageImpl<>(
                List.of(
                        new CommentEntity(1L, "Title 1", "Message 1", new UserEntity()),
                        new CommentEntity(2L, "Title 2", "Message 2", new UserEntity())
                ),
                PageRequest.of(1, 1),
                1
        );
    }

    public static List<CommentEntity> getCommentsList() {
        return List.of(
                    new CommentEntity(1L, "Title 1", "Message 1", new UserEntity()),
                    new CommentEntity(2L, "Title 2", "Message 2", new UserEntity())
        );
    }

    public static List<CommentDto> getCommentsDto() {
        return List.of(
                new CommentDto(1L, "Title 1", "Message 1", new UserDto()),
                new CommentDto(2L, "Title 2", "Message 2", new UserDto())
        );
    }

    public static CommentEntity getComment() {
        return new CommentEntity(
                1L,
                "My comment",
                "My message",
                new UserEntity()
        );
    }

    public static CommentDto getCommentDto() {
        return new CommentDto(
                1L,
                "My comment",
                "My message",
                new UserDto()
        );
    }

    public static CommentRequest getCommentRequest() {
        return new CommentRequest(
                "title",
                "message",
                1L
        );
    }
}
