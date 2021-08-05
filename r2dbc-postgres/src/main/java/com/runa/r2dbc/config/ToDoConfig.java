package com.runa.r2dbc.config;

import java.time.Duration;

import com.runa.r2dbc.domain.ToDo;
import com.runa.r2dbc.repository.ToDoRepository;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

@Configuration
public class ToDoConfig {
//        extends AbstractR2dbcConfiguration {
//
//    @Override
//    public ConnectionFactory connectionFactory() {
//        return ConnectionFactories.get(
//                ConnectionFactoryOptions.builder()
//                        .option(DRIVER, "postgresql")
//                        .option(HOST, "localhost")
//                        .option(PORT, 5432)
//                        .option(USER, "postgres")
//                        .option(PASSWORD, "postgres")
//                        .option(DATABASE, "postgres")
//                        .build());;
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