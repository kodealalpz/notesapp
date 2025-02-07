package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.request.TaskRequest;
import com.kodeala.notesapp.domain.dto.response.ApiResponse;
import com.kodeala.notesapp.domain.dto.response.TaskResponse;
import com.kodeala.notesapp.domain.service.TaskService;
import com.kodeala.notesapp.web.exception.ResourceNotFoundException;
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
    public ResponseEntity<ApiResponse<TaskResponse>> getTask(@PathVariable("taskId") int taskId) {
        TaskResponse taskResponse = taskService.getTask(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return ResponseEntity.ok(new ApiResponse<>("success", "Task found", taskResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> createTask(@RequestBody TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return new ResponseEntity<>(new ApiResponse<>(
                "success",
                "Task created",
                taskResponse
        ), HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable int taskId, @RequestBody TaskRequest request) {
        TaskResponse taskResponse = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(new ApiResponse<>(
                "success",
                "Task updated successfully",
                taskResponse
        ));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable("taskId") int taskId) {
        if(taskService.deleteTask(taskId)) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "success",
                    "Task deleted",
                    null
            ));
        }else{
            return new ResponseEntity<>(
                    new ApiResponse<>("error", "Task not found to delete", null),
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
