package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.domain.repository.GroupRepository;
import com.kodeala.notesapp.persistence.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public List<GroupResponse> getGroups(int userId) {
        return GroupMapper.toGroupsDTO(groupRepository.getAllByUsername(userId));
    }

    public Optional<GroupResponse> getGroup(int groupId) {
        return Optional.of(GroupMapper.toGroupDTO(
                groupRepository.getByGroupId(groupId)
                        .orElseGet(null)
        ));
    }
}
