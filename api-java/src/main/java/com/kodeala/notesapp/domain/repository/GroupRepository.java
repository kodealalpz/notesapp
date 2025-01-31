package com.kodeala.notesapp.domain.repository;

import com.kodeala.notesapp.persistence.crud.GroupCrudRepository;
import com.kodeala.notesapp.persistence.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GroupRepository {

    @Autowired
    private GroupCrudRepository groupCrudRepository;

    public List<Group> getAllByUsername(int userId) {
        return groupCrudRepository.findByUserId(userId)
                .orElseGet(ArrayList::new);
    }

    public Optional<Group> getByGroupId(int groupId) {
        return groupCrudRepository.findById(groupId);
    }
}
