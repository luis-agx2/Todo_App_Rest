package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.data.Label;
import com.lag.todoapp.todoapp.data.User;
import com.lag.todoapp.todoapp.entity.LabelEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.LabelMapper;
import com.lag.todoapp.todoapp.model.CustomUserDetails;
import com.lag.todoapp.todoapp.model.filter.LabelFilter;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import com.lag.todoapp.todoapp.repository.LabelRepository;
import com.lag.todoapp.todoapp.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class LabelServiceImplTest {
    @Mock
    private LabelRepository labelRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    LabelMapper labelMapper;

    @InjectMocks
    private LabelServiceImpl labelServiceImpl;

    @Nested
    @DisplayName("Get tests")
    class GetTest {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must return all labels - Admin")
        void testFindAllAdmin() {
            Mockito.when(labelRepository.findAllFilteredAndPaginated(Mockito.anyString(), Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(Label.getLabelsPage());
            Mockito.when(labelMapper.toDtosListAdmin(Mockito.anyList())).thenReturn(Label.getLabelsDtoList());

            Page<LabelDto> labelPage = labelServiceImpl.findAllFilteredAndPaginated(new LabelFilter("", ""), PageRequest.of(1, 1));

            Assertions.assertNotNull(labelPage.getContent());
            Assertions.assertFalse(labelPage.getContent().isEmpty());
        }

        @Test
        @DisplayName("Must find by id - Admin")
        void testFindByIdAdmin() {
            Mockito.when(labelRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Label.getLabelEntity()));
            Mockito.when(labelMapper.toDtoAdmin(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelDto());

            LabelDto labelDto = labelServiceImpl.findByIdAdmin(1L);

            Assertions.assertNotNull(labelDto);
        }

        @Test
        @DisplayName("Must throw error if label by id not exist - Admin")
        void testFindByIdAdminError() {
            Mockito.when(labelRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                labelServiceImpl.findByIdAdmin(0L);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
        }

        @Test
        @DisplayName("Must return all labels - User")
        void testFindAllUser() {
            Mockito.when(labelRepository.findAllByUserId(Mockito.anyLong())).thenReturn(Label.getLabelsList());
            Mockito.when(labelMapper.toDtosList(Mockito.anyList())).thenReturn(Label.getLabelsDtoList());

            List<LabelDto> labelDtoList = labelServiceImpl.findAll(userDetails);

            Assertions.assertNotNull(labelDtoList);
            Assertions.assertFalse(labelDtoList.isEmpty());
        }

        @Test
        @DisplayName("Must find by id - User")
        void testFindByIdUser() {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Label.getLabelEntity()));
            Mockito.when(labelMapper.toDto(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelDto());

            LabelDto labelDto = labelServiceImpl.findById(1L, userDetails);

            Assertions.assertNotNull(labelDto);
        }

        @Test
        @DisplayName("Must throw error if label by id not exist - User")
        void testFindByIdUserError() {
            Mockito.when(labelRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                labelServiceImpl.findById(0L, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
        }
    }

    @Nested
    @DisplayName("Creation tests")
    class CreateTest {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must create a label")
        void testCreate() {
            Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelEntity());
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(User.getUser()));
            Mockito.when(labelMapper.toEntity(Mockito.any(LabelRequest.class))).thenReturn(Label.getLabelEntity());
            Mockito.when(labelMapper.toDto(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelDto());
            LabelRequest labelRequest = Label.getLabelRequest();

            LabelDto labelDto = labelServiceImpl.create(labelRequest, userDetails);

            Assertions.assertNotNull(labelDto);
        }

        @Test
        @DisplayName("Must throw error if user not exist")
        void testCreateError() {
            Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
            LabelRequest labelRequest = Label.getLabelRequest();

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
               labelServiceImpl.create(labelRequest, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(labelRepository, Mockito.never()).save(Mockito.any(LabelEntity.class));
        }
    }

    @Nested
    @DisplayName("Modification tests")
    class UpdateTest {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must update a label")
        void testUpdateById() throws ValidationErrorException {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Label.getLabelEntity()));
            Mockito.when(labelRepository.findByNameAndUserIdAndIdIsNot(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());
            Mockito.when(labelMapper.toEntity(Mockito.any(LabelRequest.class), Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelEntity());
            Mockito.when(labelRepository.save(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelEntity());
            Mockito.when(labelMapper.toDto(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelDto());
            LabelRequest labelRequest = Label.getLabelRequest();

            LabelDto labelDto = labelServiceImpl.updateyId(labelRequest, 1L, userDetails);

            Assertions.assertNotNull(labelDto);
        }

        @Test
        @DisplayName("Must throw error if label not exist")
        void testUpdateByIdError() {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());
            LabelRequest labelRequest = Label.getLabelRequest();

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                labelServiceImpl.updateyId(labelRequest, 0L, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(labelRepository, Mockito.never()).save(Mockito.any(LabelEntity.class));
        }

        @Test
        @DisplayName("Must throw error if the label name already exist")
        void testUpdateByIdError2() {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Label.getLabelEntity()));
            Mockito.when(labelRepository.findByNameAndUserIdAndIdIsNot(Mockito.anyString(), Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(Label.getLabelEntity()));
            LabelRequest request = Label.getLabelRequest();

            Exception exception = Assertions.assertThrows(ValidationErrorException.class, () -> {
               labelServiceImpl.updateyId(request, 1L, userDetails);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(ValidationErrorException.class, exception.getClass());
        }
    }

    @Nested
    @DisplayName("Deletion Test")
    class DeleteTest {
        CustomUserDetails userDetails = new CustomUserDetails(1L, "username", "123456", true, true, true, true, Set.of());

        @Test
        @DisplayName("Must delete label by id")
        void deleteByIdTest() {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.any())).thenReturn(Optional.of(Label.getLabelEntity()));
            Mockito.when(labelMapper.toDto(Mockito.any(LabelEntity.class))).thenReturn(Label.getLabelDto());

            LabelDto labelDto = labelServiceImpl.deleteById(1L, userDetails);

            Assertions.assertNotNull(labelDto);
            Mockito.verify(labelRepository, Mockito.atMostOnce()).delete(Mockito.any(LabelEntity.class));
        }

        @Test
        @DisplayName("Must throw error if label by id not exist")
        void deleteByIdTestError() {
            Mockito.when(labelRepository.findByIdAndUserId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class,() -> {
                labelServiceImpl.deleteById(1L, userDetails);
            });


            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(labelRepository, Mockito.never()).delete(Mockito.any(LabelEntity.class));
        }
    }
}