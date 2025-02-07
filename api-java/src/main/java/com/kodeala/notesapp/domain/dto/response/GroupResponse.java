package com.kodeala.notesapp.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupResponse {
    private Integer id;
    private String name;
    private Integer totalTasks;
    private Integer completedTasks;
    private List<TaskResponse> tasks;
}
