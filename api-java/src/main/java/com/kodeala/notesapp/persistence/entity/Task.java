package com.kodeala.notesapp.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Boolean checked;

    @Column(name = "group_id")
    private Integer groupId;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable = false, updatable = false)
    private Group group;
}
