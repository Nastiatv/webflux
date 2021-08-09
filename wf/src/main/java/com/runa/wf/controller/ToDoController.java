package com.runa.wf.controller;


import com.runa.wf.domain.ToDo;
import com.runa.wf.repository.ToDoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ToDoController {

    private ToDoRepository repository;

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getToDo(@PathVariable String id) {
        return this.repository.findById(id);
    }

    @GetMapping("/todo")
    public Flux<ToDo> getToDos() {

        return this.repository.findAll();
    }

    @PostMapping("/todo")
    public Mono<ToDo> createToDo(ToDo toDo) {
        return this.repository.save(toDo);
    }
}