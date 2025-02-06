package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.request.GroupRequest;
import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.domain.repository.GroupRepository;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.persistence.entity.Group;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.GroupMapper;
import com.kodeala.notesapp.web.exception.ResourceNotFoundException;
import com.kodeala.notesapp.web.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public List<GroupResponse> getGroups() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails user) {
            User userOpt = userRepository.getByUsername(user.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the user"));

            int userId = userOpt.getId();

            List<Group> groups = groupRepository.getAllByUserId(userId);

            return GroupMapper.toGroupsDTO(groups);
        }

        return Collections.emptyList();
    }

    public Optional<GroupResponse> getGroup(int groupId) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails user) {
            User userOpt = userRepository.getByUsername(user.getUsername())
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the user"));

            int userId = userOpt.getId();

            Group group = groupRepository.getByGroupId(groupId)
                    .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

            if(group.getUserId() != userId)
                throw new UnauthorizedException("You can't access to this resource");

            return Optional.of(GroupMapper.toGroupDTO(group));
        }

        return Optional.empty();
    }

    public GroupResponse createGroup(GroupRequest groupRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof  UserDetails user) {
            User userOpt = userRepository.getByUsername((user.getUsername()))
                    .orElseThrow(() -> new ResourceNotFoundException("There is a problem with the user"));

            int userId = userOpt.getId();

            if(userId != groupRequest.getUserId()) {
                throw new IllegalArgumentException("There is a problem with the user id");
            }

            Group group = GroupMapper.toGroup(groupRequest);
            group.setTasks(Collections.emptyList());
            return GroupMapper.toGroupDTO(groupRepository.create(group));
        }

        return new GroupResponse();
    }

    public boolean deleteGroup(int groupId) {
        return getGroup(groupId).map(group -> {
            groupRepository.delete(groupId);
            return true;
        }).orElse(false);
    }
}
