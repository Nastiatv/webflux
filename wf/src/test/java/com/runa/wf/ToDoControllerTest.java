package com.runa.wf;

import com.runa.wf.domain.ToDo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest
@AutoConfigureWebTestClient
class ToDoControllerTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void testGetToDo() {
        webClient.get().uri("/todo")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(ToDo.class);

    }

    @Test
    public void getAll() {
        webClient.get()
                .uri("/todo")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$")
                .isArray()
                .jsonPath("$[0].description")
                .isEqualTo("Do homework")
                .jsonPath("$[1].description")
                .isEqualTo("Workout in the mornings")
                .jsonPath("$[2].description")
                .isEqualTo("Make dinner tonight")
                .jsonPath("$[3].description")
                .isEqualTo("Clean the studio");
    }
}