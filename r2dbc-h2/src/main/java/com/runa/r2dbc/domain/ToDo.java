package com.runa.r2dbc.domain;

import java.time.LocalDateTime;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ToDo {

    @Id
    private Long id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo() {
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }

    public ToDo(String description, boolean completed) {
        this();
        this.description = description;
        this.completed = completed;
    }
}
