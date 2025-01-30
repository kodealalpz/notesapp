package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.response.TaskResponse;
import com.kodeala.notesapp.persistence.entity.Task;

public class TaskMapper {
    public static TaskResponse toTaskDTO(Task task) {
        TaskResponse taskDTO = new TaskResponse();

        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setChecked(task.getChecked());

        return taskDTO;
    }
}
