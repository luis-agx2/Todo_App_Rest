package com.lag.todoapp.todoapp.data;

import com.lag.todoapp.todoapp.entity.LabelEntity;
import com.lag.todoapp.todoapp.entity.UserEntity;
import com.lag.todoapp.todoapp.model.request.LabelRequest;
import com.lag.todoapp.todoapp.model.response.LabelDto;
import com.lag.todoapp.todoapp.model.response.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class Label {
    public static Page<LabelEntity> getLabelsPage() {
        return new PageImpl<>(
                getLabelsList(),
                PageRequest.of(1, 1),
                getLabelsList().size()
        );
    }

    public static List<LabelEntity> getLabelsList() {
        return List.of(
                new LabelEntity(1L , "Label 1", "#fffff", new UserEntity()),
                new LabelEntity(2L , "Label 2", "#00000", new UserEntity())
        );
    }

    public static List<LabelDto> getLabelsDtoList() {
        return List.of(
                new LabelDto(1L, "Label 1", "#fffff", new UserDto()),
                new LabelDto(2L, "Label 2", "#00000", new UserDto())
        );
    }

    public static LabelEntity getLabelEntity() {
        return new LabelEntity(1L, "Label 1", "#fffff", new UserEntity());
    }

    public static LabelDto getLabelDto() {
        return new LabelDto(1L, "Label 1", "#ffffff", new UserDto());
    }

    public static LabelRequest getLabelRequest() {
        return new LabelRequest("Label", "#ffffff");
    }
}
