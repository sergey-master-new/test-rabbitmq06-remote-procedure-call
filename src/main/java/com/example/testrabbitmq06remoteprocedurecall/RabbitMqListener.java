package com.example.testrabbitmq06remoteprocedurecall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Random;

@EnableRabbit
@Component
public class RabbitMqListener {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RabbitListener(queues = "query-example-6")
    public String worker1(String message) throws InterruptedException {
        log.info("Received on worker : " + message);
        Thread.sleep(3000);
        return "Received on worker : " + message;
    }
}
