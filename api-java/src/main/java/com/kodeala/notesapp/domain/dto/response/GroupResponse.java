package com.kodeala.notesapp.domain.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GroupResponse {
    private Integer id;
    private String name;
    private List<TaskResponse> tasks;
}
