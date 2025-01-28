package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.UserDTO;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUsers() {
        return userRepository.getAll();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}
