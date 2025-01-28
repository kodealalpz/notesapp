package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.UserDTO;
import com.kodeala.notesapp.domain.service.UserService;
import com.kodeala.notesapp.persistence.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("userEmail") String userEmail) {
        return userService.getUserByEmail(userEmail)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
