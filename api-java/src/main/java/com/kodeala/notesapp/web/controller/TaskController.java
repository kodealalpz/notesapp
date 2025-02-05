package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.request.TaskRequest;
import com.kodeala.notesapp.domain.dto.response.TaskResponse;
import com.kodeala.notesapp.domain.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskResponse> getTask(@PathVariable("taskId") int taskId) {
        return taskService.getTask(taskId)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<>(
                taskService.createTask(taskRequest),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("taskId") int taskId) {
        return new ResponseEntity<>(taskService.deleteTask(taskId), HttpStatus.OK);
    }
}
