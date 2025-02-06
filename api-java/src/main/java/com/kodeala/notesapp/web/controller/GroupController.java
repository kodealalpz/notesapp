package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.request.GroupRequest;
import com.kodeala.notesapp.domain.dto.response.ApiResponse;
import com.kodeala.notesapp.domain.dto.response.GroupResponse;
import com.kodeala.notesapp.domain.service.GroupService;
import com.kodeala.notesapp.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("/{groupId}")
    public ResponseEntity<ApiResponse<GroupResponse>> getGroup(@PathVariable("groupId") int groupId) {
        GroupResponse groupResponse = groupService.getGroup(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        return ResponseEntity.ok(new ApiResponse<>("success", "Group found", groupResponse));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupResponse>>> getGroups() {
        return ResponseEntity.ok(new ApiResponse<>(
           "success",
           "Groups found",
           groupService.getGroups()
        ));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.createGroup(groupRequest);
        return new ResponseEntity<>(new ApiResponse<>(
                "success",
                "Group created",
                groupResponse
        ), HttpStatus.CREATED);
    }

    @DeleteMapping("/{groupId}")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(@PathVariable("groupId") int groupId) {
        if(groupService.deleteGroup(groupId)) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "success",
                    "Group deleted",
                    null
            ));
        }else{
            return new ResponseEntity<>(
                    new ApiResponse<>("error", "Group not found to delete", null),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
