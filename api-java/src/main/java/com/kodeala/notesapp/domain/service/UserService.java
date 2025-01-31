package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.response.UserResponse;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserResponse> getUsers() {
        return UserMapper.toUsersDTO(userRepository.getAll());
    }
}
