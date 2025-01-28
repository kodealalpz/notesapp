package com.kodeala.notesapp.persistence.crud;

import com.kodeala.notesapp.persistence.entity.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupCrudRepository extends CrudRepository<Group, Integer> {

    Optional<List<Group>> findByUserId(int userId);
}
