package com.lag.todoapp.todoapp.data;

import com.lag.todoapp.todoapp.entity.PriorityEntity;
import com.lag.todoapp.todoapp.model.request.PriorityRequest;
import com.lag.todoapp.todoapp.model.response.PriorityDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class Priority {
    public static Page<PriorityEntity> getPriorityPageable() {
        return new PageImpl<>(
          List.of(
                  new PriorityEntity(1L, "Low"),
                  new PriorityEntity(2L, "Medium")
          )
        );
    }

    public static List<PriorityEntity> getPriorityList() {
        return List.of(
                new PriorityEntity(1L, "Low"),
                new PriorityEntity(2L, "Medium")
        );
    }

    public static PriorityEntity getPriority() {
        return new PriorityEntity(1L, "Low");
    }

    public static List<PriorityDto> getPriorityDtoList() {
        return List.of(
                new PriorityDto(1L, "Low"),
                new PriorityDto(2L, "Medium")
        );
    }

    public static PriorityDto getPriorityDto() {
        return new PriorityDto(1L, "High");
    }

    public static PriorityRequest getPriorityRequest() {
        return new PriorityRequest("None");
    }
}
