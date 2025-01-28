package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.GroupDTO;
import com.kodeala.notesapp.domain.dto.UserDTO;
import com.kodeala.notesapp.persistence.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setGroups(
                user.getGroups().stream()
                        .map(GroupMapper::toGroupDTO)
                        .collect(Collectors.toList())
        );

        return userDTO;
    }

    public static List<UserDTO> toUsersDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();

        users.forEach(user -> {
            usersDTO.add(UserMapper.toUserDTO(user));
        });

        return usersDTO;
    }
}
