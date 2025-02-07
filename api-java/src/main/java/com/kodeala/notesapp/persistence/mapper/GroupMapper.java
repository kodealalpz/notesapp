package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.request.GroupRequest;
import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.persistence.entity.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupResponse toGroupDTO(Group group) {
        GroupResponse groupDTO = new GroupResponse();

        groupDTO.setId(group.getId());
        groupDTO.setName(group.getName());
        groupDTO.setTotalTasks(group.getTotalTasks());
        groupDTO.setCompletedTasks(group.getTasksCompleted());
        groupDTO.setTasks(
                group.getTasks().stream()
                        .map(TaskMapper::toTaskDTO)
                        .collect(Collectors.toList())
        );

        return groupDTO;
    }

    public static List<GroupResponse> toGroupsDTO(List<Group> groups) {
        List<GroupResponse> groupsDTO = new ArrayList<>();

        groups.forEach(group ->
            groupsDTO.add(GroupMapper.toGroupDTO(group)));

        return groupsDTO;
    }

    public static Group toGroup(GroupRequest groupRequest) {
        return Group.builder()
                .name(groupRequest.getName())
                .userId(groupRequest.getUserId())
                .build();
    }
}
