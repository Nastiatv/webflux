package com.runa.wf;

import com.runa.wf.domain.ToDo;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WfApplicationTests {

    private ToDo homework = new ToDo("Do homework");
    private ToDo workout = new ToDo("Workout in the mornings", true);
    private ToDo dinner = new ToDo("Make dinner tonight");

    @Test
    void mono() {
        Mono<ToDo> monoHomework = Mono.just(homework);
        ToDo homework2 = monoHomework.block();
        assertEquals(homework, homework2);
    }

    @Test
    public void blockMono() {
        Mono<ToDo> monoHomework = Mono.just(homework);
        String description = monoHomework.map(ToDo::getDescription).block();
        assertEquals(description, "Do homework");
    }

    @Test
    public void flux() {
        Flux<ToDo> fluxToDos = Flux.just(homework, workout, dinner);
        fluxToDos.subscribe(System.out::println);
    }

    @Test
    public void fluxDelayElements() {
        Flux<ToDo> fluxToDos = Flux.just(homework, workout, dinner);
        fluxToDos.delayElements(Duration.ofSeconds(1))
                .subscribe(System.out::println);
    }

    @Test
    public void fluxDelayElementsCountDownLatch() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux<ToDo> fluxToDos = Flux.just(homework, workout, dinner);
        fluxToDos.delayElements(Duration.ofSeconds(1))
                .doOnComplete(countDownLatch::countDown)
                .subscribe(System.out::println); // вывод каждую секунду
        countDownLatch.await();
    }

    @Test
    void mapExample() {
        Flux<String> fluxColors = Flux.just("red", "green", "blue");
        fluxColors.map(color -> color.charAt(0)).subscribe(System.out::println);
    }

    @Test
    void zipExample() {
        Flux<String> fluxFruits = Flux.just("apple", "pear", "plum");
        Flux<String> fluxColors = Flux.just("red", "green", "blue");
        Flux<Integer> fluxAmounts = Flux.just(10, 20, 30);
        Flux.zip(fluxFruits, fluxColors, fluxAmounts).subscribe(System.out::println);
    }

    @Test
    public void onErrorExample() {
        Flux<String> fluxCalc = Flux.just(-1, 0, 1)
                .map(i -> "10 / " + i + " = " + (10 / i));

        fluxCalc.subscribe(value -> System.out.println("Next: " + value),
                error -> System.err.println("Error: " + error));
    }

    @Test
    public void onErrorReturnExample() {
        Flux<String> fluxCalc = Flux.just(-1, 0, 1)
                .map(i -> "10 / " + i + " = " + (10 / i))
                .onErrorReturn(ArithmeticException.class, "Division by 0 not allowed");

        fluxCalc.subscribe(value -> System.out.println("Next: " + value),
                error -> System.err.println("Error: " + error));
    }

    @Test
    public void backpressureExample() {
        Flux.range(1, 5)
                .subscribe(new Subscriber<Integer>() {
                    private Subscription s;
                    int counter;

                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe");
                        this.s = s;
                        System.out.println("Requesting 2 emissions");
                        s.request(2);
                    }

                    @Override
                    public void onNext(Integer i) {
                        System.out.println("onNext " + i);
                        counter++;
                        if (counter % 2 == 0) {
                            System.out.println("Requesting 2 emissions");
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.err.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
    }

    @Test
    public void coldPublisherExample() throws InterruptedException {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        Thread.sleep(2000);
        intervalFlux.subscribe(i -> System.out.println(String.format("Subscriber A, value: %d", i)));
        Thread.sleep(2000);
        intervalFlux.subscribe(i -> System.out.println(String.format("Subscriber B, value: %d", i)));
        Thread.sleep(3000);
    }

    @Test
    public void hotPublisherExample() throws InterruptedException {
        Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1));
        ConnectableFlux<Long> intervalCF = intervalFlux.publish();
        intervalCF.connect();
        Thread.sleep(2000);
        intervalCF.subscribe(i -> System.out.println(String.format("Subscriber A, value: %d", i)));
        Thread.sleep(2000);
        intervalCF.subscribe(i -> System.out.println(String.format("Subscriber B, value: %d", i)));
        Thread.sleep(3000);
    }
}