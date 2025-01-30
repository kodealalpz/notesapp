package com.kodeala.notesapp.domain.dto.response;

import lombok.Data;

@Data
public class TaskResponse {
    private Integer id;
    private String description;
    private Boolean checked;
}
