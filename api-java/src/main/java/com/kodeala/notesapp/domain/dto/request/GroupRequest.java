package com.kodeala.notesapp.domain.dto.request;

import lombok.Data;

@Data
public class GroupRequest {
    private String name;
    private int userId;
}
