package com.runa.wf;

import com.runa.wf.repository.ToDoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
class ToDoRepositoryTest {

    @Autowired
    ToDoRepository repository;

    @Test
    void readsAllEntitiesCorrectly() {

        repository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(4)
                .verifyComplete();
    }
}