package com.runa.r2dbc.repository;

import com.runa.r2dbc.domain.ToDo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends ReactiveCrudRepository<ToDo, Long> {
}

