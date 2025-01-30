package com.kodeala.notesapp.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String email;
//    private List<GroupResponse> groups;
}
