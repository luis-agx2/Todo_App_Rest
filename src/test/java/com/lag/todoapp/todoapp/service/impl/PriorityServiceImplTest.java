package com.lag.todoapp.todoapp.service.impl;

import com.lag.todoapp.todoapp.config.TestContainersConfig;
import com.lag.todoapp.todoapp.data.Priority;
import com.lag.todoapp.todoapp.entity.PriorityEntity;
import com.lag.todoapp.todoapp.exception.NotFoundException;
import com.lag.todoapp.todoapp.exception.ValidationErrorException;
import com.lag.todoapp.todoapp.mapper.PriorityMapper;
import com.lag.todoapp.todoapp.model.filter.PriorityFilter;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.model.response.PriorityDto;
import com.lag.todoapp.todoapp.repository.PriorityRepository;
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

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles(value = "test")
@ContextConfiguration(classes = TestContainersConfig.class)
class PriorityServiceImplTest {
    @Mock
    private PriorityRepository priorityRepository;

    @Mock
    private PriorityMapper priorityMapper;

    @InjectMocks
    private PriorityServiceImpl priorityService;

    @Nested
    @DisplayName("Get tests")
    class GetTests {
        @Test
        @DisplayName("Must return priorities in pageable format")
        void testFindAllFiltered() {
            Mockito.when(priorityRepository.findAllFilteredAndPaginated(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(Priority.getPriorityPageable());
            Mockito.when(priorityMapper.toDtoList(Mockito.anyList())).thenReturn(Priority.getPriorityDtoList());

            Page<PriorityDto> page = priorityService.findAllFiltered(new PriorityFilter(""), PageRequest.of(1, 1));

            Assertions.assertNotNull(page);
            Assertions.assertFalse(page.getContent().isEmpty());
        }

        @Test
        @DisplayName("Must return all priorities")
        void testFindAll() {
            Mockito.when(priorityRepository.findAllFilteredAndPaginated(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(Priority.getPriorityPageable());
            Mockito.when(priorityMapper.toDtoList(Mockito.anyList())).thenReturn(Priority.getPriorityDtoList());

            List<PriorityDto> dtoList = priorityService.findAll();

            Assertions.assertNotNull(dtoList);
            Assertions.assertFalse(dtoList.isEmpty());
        }

        @Test
        @DisplayName("Must find a priority by id")
        void testFindByIdSuccess() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Priority.getPriority()));
            Mockito.when(priorityMapper.toDto(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriorityDto());

            PriorityDto priorityDto = priorityService.findById(1L);

            Assertions.assertNotNull(priorityDto);
            Mockito.verify(priorityMapper, Mockito.atMostOnce()).toDto(Mockito.any(PriorityEntity.class));
        }

        @Test
        @DisplayName("Must throw error due to priority not exist")
        void testFindIdFailed() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
                priorityService.findById(0L);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(priorityMapper, Mockito.never()).toDto(Mockito.any(PriorityEntity.class));
        }
    }

    @Nested
    @DisplayName("Creations tests")
    class CreationTests {
        @Test
        @DisplayName("Must create a priority")
        void testCreate() {
            Mockito.when(priorityMapper.toEntity(Mockito.any(PriorityRequest.class))).thenReturn(Priority.getPriority());
            Mockito.when(priorityRepository.save(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriority());
            Mockito.when(priorityMapper.toDto(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriorityDto());

            PriorityDto priority = priorityService.create(Priority.getPriorityRequest());

            Assertions.assertNotNull(priority);
            Mockito.verify(priorityRepository, Mockito.atMostOnce()).save(Mockito.any(PriorityEntity.class));
        }

        @Test
        @DisplayName("Must throw error at create")
        void testCreateFailed() {
            Mockito.when(priorityMapper.toEntity(Mockito.any(PriorityRequest.class))).thenThrow();
            PriorityRequest request = Priority.getPriorityRequest();

            Exception exception = Assertions.assertThrows(Exception.class, () -> {
               priorityService.create(request);
            });

            Assertions.assertNotNull(exception);
            Mockito.verify(priorityRepository, Mockito.never()).save(Mockito.any(PriorityEntity.class));
        }
    }

    @Nested
    @DisplayName("Modifications tests")
    class ModificationsTests {
        @Test
        @DisplayName("Must update a priority")
        void testUpdateByIdSuccess() throws ValidationErrorException {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Priority.getPriority()));
            Mockito.when(priorityMapper.toEntity(Mockito.any(PriorityRequest.class), Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriority());
            Mockito.when(priorityMapper.toDto(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriorityDto());
            Mockito.when(priorityRepository.save(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriority());

            PriorityRequest request = Priority.getPriorityRequest();

            PriorityDto priorityDto = priorityService.updateById(1L, request);

            Assertions.assertNotNull(priorityDto);
            Mockito.verify(priorityRepository, Mockito.atMostOnce()).save(Mockito.any(PriorityEntity.class));
        }

        @Test
        @DisplayName("Must throw error at modification priority")
        void testUpdateByIdFailed() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
            Mockito.when(priorityMapper.toEntity(Mockito.any(PriorityRequest.class), Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriority());
            PriorityRequest request = new PriorityRequest();

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
               priorityService.updateById(0L, request);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(priorityRepository, Mockito.never()).save(Mockito.any(PriorityEntity.class));
        }

        @Test
        @DisplayName("Must throw error due to label name is already registered")
        void testUpdateByIdFailed2() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Priority.getPriority()));
            Mockito.when(priorityRepository.findByNameAndIdIsNot(Mockito.anyString(), Mockito.anyLong())).thenReturn(Optional.of(Priority.getPriority()));

            PriorityRequest request = Priority.getPriorityRequest();

            Exception exception = Assertions.assertThrows(ValidationErrorException.class, () -> {
                priorityService.updateById(0L, request);
            });

            Assertions.assertNotNull(exception);
        }
    }

    @Nested
    @DisplayName("Deletions tests")
    class DeletionsTest {
        @Test
        @DisplayName("Must delete a priority by id")
        void testDeleteByIdSuccess() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(Priority.getPriority()));
            Mockito.doNothing().when(priorityRepository).delete(Mockito.any(PriorityEntity.class));
            Mockito.when(priorityMapper.toDto(Mockito.any(PriorityEntity.class))).thenReturn(Priority.getPriorityDto());

            PriorityDto priorityDto = priorityService.deleteById(1L);

            Assertions.assertNotNull(priorityDto);
            Mockito.verify(priorityRepository, Mockito.atMostOnce()).delete(Mockito.any(PriorityEntity.class));
        }

        @Test
        @DisplayName("Must throw error at delete priority")
        void testDeleteByIdFailed() {
            Mockito.when(priorityRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

            Exception exception = Assertions.assertThrows(NotFoundException.class, () -> {
               priorityService.deleteById(0L);
            });

            Assertions.assertNotNull(exception);
            Assertions.assertEquals(NotFoundException.class, exception.getClass());
            Mockito.verify(priorityRepository, Mockito.never()).delete(Mockito.any(PriorityEntity.class));
        }
    }
}