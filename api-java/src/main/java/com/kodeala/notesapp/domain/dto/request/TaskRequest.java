package com.kodeala.notesapp.domain.dto.request;

import lombok.Data;

@Data
public class TaskRequest {
    private String description;
    private boolean checked;
    private int groupId;
}
