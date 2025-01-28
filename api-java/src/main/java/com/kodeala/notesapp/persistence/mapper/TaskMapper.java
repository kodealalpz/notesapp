package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.TaskDTO;
import com.kodeala.notesapp.persistence.entity.Task;

public class TaskMapper {
    public static TaskDTO toTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setChecked(task.getChecked());

        return taskDTO;
    }
}
