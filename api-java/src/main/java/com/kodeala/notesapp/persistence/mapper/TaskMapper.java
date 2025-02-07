package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.request.TaskRequest;
import com.kodeala.notesapp.domain.dto.response.TaskResponse;
import com.kodeala.notesapp.persistence.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskMapper {
    public static TaskResponse toTaskDTO(Task task) {
        TaskResponse taskDTO = new TaskResponse();

        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setChecked(task.getChecked());

        return taskDTO;
    }

    public static List<TaskResponse> toTasksDTO(List<Task> tasks) {
        List<TaskResponse> tasksDTO = new ArrayList<>();

        tasks.forEach(task ->
                tasksDTO.add(TaskMapper.toTaskDTO(task)));

        return tasksDTO;
    }

    public static Task toTask(TaskRequest taskRequest) {
        return Task.builder()
                .description(taskRequest.getDescription())
                .checked(taskRequest.isChecked())
                .groupId(taskRequest.getGroupId())
                .build();
    }
}
