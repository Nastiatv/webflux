package com.runa.r2dbc.controller;

import com.runa.r2dbc.domain.ToDo;
import com.runa.r2dbc.repository.ToDoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<ToDo>> getToDo(@PathVariable Long id) {
        return this.repository.findById(id)
                .map(toDo -> new ResponseEntity<>(toDo, HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(null, HttpStatus.NOT_FOUND)));
    }
}