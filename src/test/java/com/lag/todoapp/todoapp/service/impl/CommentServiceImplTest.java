package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.data.Comment;
import com.lag.todoapp.todoapp.entity.CommentEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.mapper.CommentMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.CommentFilter;
import com.lag.todoapp.todoapp.model.request.CommentRequest;
import com.lag.todoapp.todoapp.model.response.CommentDto;
import com.lag.todoapp.todoapp.repository.CommentRepository;
import com.lag.todoapp.todoapp.repository.TaskRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Nested
    @DisplayName("Get tests")
    class GetTest {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "nickname", "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must return all tasks - Admin")
        void  testFindAllAdmin() {
            Mockito.when(commentRepository.findAllFilteredAndPaginated(Mockito.anyString(), Mockito.anyLong(), Mockito.any(LocalDate.class), Mockito.any(Pageable.class))).thenReturn(Comment.getCommentsPageable());
            Mockito.when(commentMapper.toCommentDtoList(Mockito.anyList())).thenReturn(Comment.getCommentsDto());

            Page<CommentDto> result = commentService.findAllFilteredAndPaginated(new CommentFilter("title", 1L, LocalDate.now()), PageRequest.of(1, 1));

            Assertions.assertNotNull(result);
            Assertions.assertFalse(result.getContent().isEmpty());
        }

        @Test
        @DisplayName("Must find comment by id - Admin")
        void testFindByIdAdmin() {
            Mockito.when(commentRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Comment.getComment()));
            Mockito.when(commentMapper.toDto(Mockito.any(CommentEntity.class))).thenReturn(Comment.getCommentDto());

            CommentDto commentDto = commentService.findByIdAdmin(1L);

            Assertions.assertNotNull(commentDto);
        }

        @Test
        @DisplayName("Must throw error if comment by id not exist - Admin")
        void testFindByIdAdminError() {
            Mockito.when(commentRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                commentService.findByIdAdmin(1L);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
        }

        @Test
        @DisplayName("Must return all tasks - User")
        void testFindAllUser() {
            Mockito.when(commentRepository.findAllByUserId(Mockito.anyLong())).thenReturn(Comment.getCommentsList());
            Mockito.when(commentMapper.toCommentDtoListMe(Mockito.anyList())).thenReturn(Comment.getCommentsDto());

            List<CommentDto> result = commentService.findAllMe(userDetails);

            Assertions.assertNotNull(result);
            Assertions.assertFalse(result.isEmpty());
        }

        @Test
        @DisplayName("Must find comment by id - User")
        void testFindByIdAndUser() {
            Mockito.when(commentRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Comment.getComment()));
            Mockito.when(commentMapper.toDtoMe(Mockito.any(CommentEntity.class))).thenReturn(Comment.getCommentDto());

            CommentDto commentDto = commentService.findById(1L, userDetails);

            Assertions.assertNotNull(commentDto);
        }

        @Test
        @DisplayName("Must throw error if comment by id not exist - User")
        void testFindByIdAndUserError() {
            Mockito.when(commentRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                commentService.findById(1L, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
        }
    }

    @Nested
    @DisplayName("Modification test")
    class CreationTests {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "nickname", "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must update comment by id")
        void updateTest() {
            Mockito.when(commentRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Comment.getComment()));
            Mockito.when(commentRepository.save(Mockito.any(CommentEntity.class))).thenReturn(Comment.getComment());
            Mockito.when(commentMapper.toEntity(Mockito.any(CommentRequest.class), Mockito.any(CommentEntity.class))).thenReturn(Comment.getComment());
            Mockito.when(commentMapper.toDto(Mockito.any(CommentEntity.class))).thenReturn(Comment.getCommentDto());

            CommentDto comment = commentService.updateById(1L, new CommentRequest(), userDetails);

            Assertions.assertNotNull(comment);

            Mockito.verify(commentRepository, Mockito.atMostOnce()).save(Mockito.any(CommentEntity.class));
        }

        @Test
        @DisplayName("Must throw error if comment by id not exist - User")
        void updateErrorTest() {
            Mockito.when(commentRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());
            CommentRequest request = Comment.getCommentRequest();

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                commentService.updateById(1L, request, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
        }
    }
}