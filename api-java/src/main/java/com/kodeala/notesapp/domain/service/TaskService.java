package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.request.TaskRequest;
import com.kodeala.notesapp.domain.dto.response.TaskResponse;
import com.kodeala.notesapp.domain.repository.GroupRepository;
import com.kodeala.notesapp.domain.repository.TaskRepository;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.persistence.entity.Group;
import com.kodeala.notesapp.persistence.entity.Task;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.TaskMapper;
import com.kodeala.notesapp.web.exception.ResourceNotFoundException;
import com.kodeala.notesapp.web.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<TaskResponse> getTask(int taskId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails user) {
            User userOpt = userRepository.getByUsername(user.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the user"));
            Task task = taskRepository.getByTaskId(taskId)
                    .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

            int userId = userOpt.getId();
            Group group = groupRepository.getByGroupId(task.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the task group"));

            if(userId != group.getUserId())
                throw new UnauthorizedException("You can't access to this resource");

            return Optional.of(TaskMapper.toTaskDTO(task));
        }

        return Optional.empty();
    }

    public TaskResponse createTask(TaskRequest taskRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails user) {
            User userOpt = userRepository.getByUsername(user.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the user"));
            Group groupOpt = groupRepository.getByGroupId(taskRequest.getGroupId())
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

            int userId = userOpt.getId();
            int userId2 = groupOpt.getUserId();

            if(userId != userId2) {
                throw new IllegalArgumentException("There is a problem with user reference");
            }

            Task newTask = TaskMapper.toTask(taskRequest);
            newTask.setChecked(false);
            return TaskMapper.toTaskDTO(taskRepository.create(newTask));
        }

        return new TaskResponse();
    }

    public boolean deleteTask(int taskId) {
        return getTask(taskId).map(task -> {
            taskRepository.delete(taskId);
            return true;
        }).orElse(false);
    }
}
