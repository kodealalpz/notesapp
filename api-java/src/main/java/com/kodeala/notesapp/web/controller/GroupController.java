package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.request.GroupRequest;
import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponse> getGroup(@PathVariable("groupId") int groupId) {
        return groupService.getGroup(groupId)
                .map(group -> new ResponseEntity<>(group, HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));

    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getGroups() {
        return new ResponseEntity<>(
                groupService.getGroups(),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        return new ResponseEntity<>(
                groupService.createGroup(groupRequest),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<Boolean> deleteGroup(@PathVariable("groupId") int groupId) {
        return new ResponseEntity<>(groupService.deleteGroup(groupId), HttpStatus.OK);
    }
}
