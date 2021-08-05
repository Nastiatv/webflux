package com.runa.r2dbc.config;

import java.time.Duration;

import com.runa.r2dbc.domain.ToDo;
import com.runa.r2dbc.repository.ToDoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ToDoConfig {

//     @Bean
//    public H2ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url()
//                        .username()
//                        .build()
//        );
//    }

    @Bean
    public CommandLineRunner insertAndView(ToDoRepository repository) {
        return args -> {
            repository.save(new ToDo("Do homework")).subscribe();
            repository.save(new ToDo("Workout in the mornings", true)).
                    subscribe();
            repository.save(new ToDo("Make dinner tonight")).subscribe();
            repository.save(new ToDo("Clean the studio", true)).subscribe();
            repository.findAll().doOnNext(toDo -> {
                System.out.println(toDo.toString());
            }).blockLast(Duration.ofSeconds(1));
        };
    }
}