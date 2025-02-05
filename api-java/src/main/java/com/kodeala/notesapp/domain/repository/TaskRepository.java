package com.kodeala.notesapp.domain.repository;

import com.kodeala.notesapp.persistence.crud.TaskCrudRepository;
import com.kodeala.notesapp.persistence.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TaskRepository {

    @Autowired
    private TaskCrudRepository taskCrudRepository;

    public Optional<Task> getByTaskId(int taskId) {
        return taskCrudRepository.findById(taskId);
    }

    public Task create(Task task) {
        return taskCrudRepository.save(task);
    }

    public void delete(int groupId) {
        taskCrudRepository.deleteById(groupId);
    }
}
