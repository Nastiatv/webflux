package com.runa.r2dbc.repository;

import com.runa.r2dbc.domain.ToDo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ToDoRepository extends ReactiveCrudRepository<ToDo, Long> {

    //    @Query("SELECT * FROM to_do WHERE completed = :completed")
    Flux<ToDo> findByCompleted(boolean completed);
}

