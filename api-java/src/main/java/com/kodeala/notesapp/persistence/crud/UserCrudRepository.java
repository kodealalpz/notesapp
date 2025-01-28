package com.kodeala.notesapp.persistence.crud;

import com.kodeala.notesapp.persistence.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserCrudRepository extends CrudRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

}
