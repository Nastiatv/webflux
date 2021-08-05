package com.runa.r2dbc.controller;

import com.runa.r2dbc.domain.ToDo;
import com.runa.r2dbc.repository.ToDoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class ToDoController {

    private ToDoRepository repository;

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    public Flux<ToDo> getToDos() {
        return this.repository.findAll();
    }

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getToDo(@PathVariable Long id) {
        return this.repository.findById(id);
    }

    @GetMapping("/todo/completed/{completed}")
    public Flux<ToDo> getToDo(@PathVariable boolean completed) {
        return this.repository.findByCompleted(completed);
    }
}