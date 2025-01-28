package com.kodeala.notesapp.domain.repository;

import com.kodeala.notesapp.domain.dto.UserDTO;
import com.kodeala.notesapp.persistence.crud.UserCrudRepository;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    @Autowired
    private UserCrudRepository userCrudRepository;

    public List<UserDTO> getAll() {

        return UserMapper.toUsersDTO((List<User>) userCrudRepository.findAll());
    }

    public Optional<User> getByEmail(String email) {
        // TODO: Map the data
        return userCrudRepository.findByEmail(email);
    };

    public Optional<User> getByUsername(String username) {
        // TODO: Map the data
        return userCrudRepository.findByUsername(username);
    }

    public User create(User user) {
        return userCrudRepository.save(user);
    }

    public boolean delete(int userId) {
        return userCrudRepository.findById(userId).map(user -> {
            userCrudRepository.deleteById(userId);
            return true;
        }).orElse(false);
    }
}
