package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.persistence.entity.Group;

import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupResponse toGroupDTO(Group group) {
        GroupResponse groupDTO = new GroupResponse();

        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setTasks(
                group.getTasks().stream()
                        .map(TaskMapper::toTaskDTO)
                        .collect(Collectors.toList())
        );

        return groupDTO;
    }
}
