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
            Optional<User> userOpt = userRepository.getByUsername(user.getUsername());
            Optional<Task> task = taskRepository.getByTaskId(taskId);

            if(userOpt.isPresent() && task.isPresent()){
                int userId = userOpt.get().getId();
                Optional<Group> group = groupRepository.getByGroupId(task.get().getGroupId());

                if(group.isPresent()) {
                    if(userId != group.get().getUserId())
                        throw new IllegalArgumentException();

                    return task.map(TaskMapper::toTaskDTO);
                }
            }
        }

        return Optional.empty();
    }

    public TaskResponse createTask(TaskRequest taskRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails user) {
            Optional<User> userOpt = userRepository.getByUsername(user.getUsername());
            Optional<Group> groupOpt = groupRepository.getByGroupId(taskRequest.getGroupId());

            if(userOpt.isPresent() && groupOpt.isPresent()) {
                int userId = userOpt.get().getId();
                int userId2 = groupOpt.get().getUserId();

                if(userId != userId2) {
                    throw new IllegalArgumentException();
                }

                Task newTask = TaskMapper.toTask(taskRequest);
                newTask.setChecked(false);
                return TaskMapper.toTaskDTO(taskRepository.create(newTask));
            }
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
