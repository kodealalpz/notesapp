package com.kodeala.notesapp.persistence.mapper;

import com.kodeala.notesapp.domain.dto.request.auth.RegisterRequest;
import com.kodeala.notesapp.domain.dto.response.UserResponse;
import com.kodeala.notesapp.persistence.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserResponse toUserDTO(User user) {
        UserResponse userDTO = new UserResponse();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
//        userDTO.setGroups(
//                user.getGroups().stream()
//                        .map(GroupMapper::toGroupDTO)
//                        .collect(Collectors.toList())
//        );

        return userDTO;
    }

    public static List<UserResponse> toUsersDTO(List<User> users) {
        List<UserResponse> usersDTO = new ArrayList<>();

        users.forEach(user ->
            usersDTO.add(UserMapper.toUserDTO(user)));

        return usersDTO;
    }

    public static User toUser(RegisterRequest registerRequest) {
        return User.builder()
                .name(registerRequest.getName())
                .surname(registerRequest.getSurname())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .build();
    }
}
