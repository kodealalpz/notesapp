package com.kodeala.notesapp.persistence.crud;

import com.kodeala.notesapp.persistence.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskCrudRepository extends CrudRepository<Task, Integer> {

    @Query(value = "SELECT COUNT(t) FROM tasks t WHERE t.group_id = :groupId", nativeQuery = true)
    int countByGroupId(@Param("groupId") int groupId);

    @Query(value = "SELECT COUNT(t) FROM tasks t WHERE t.group_id = :groupId AND t.checked = true", nativeQuery = true)
    int countCompletedByGroupId(@Param("groupId") int groupId);
}
