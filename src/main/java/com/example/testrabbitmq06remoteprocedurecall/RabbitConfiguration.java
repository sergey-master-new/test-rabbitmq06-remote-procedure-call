package com.example.testrabbitmq06remoteprocedurecall;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfiguration {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.rabbitmq.host}")
    private String hostname;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    //    сonnectionFactory — для соединения с RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(hostname);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(userName);
        connectionFactory.setVirtualHost(virtualHost);

        return connectionFactory;
    }

    //    rabbitAdmin — для регистрации/отмены регистрации очередей и т.п.;
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    //    rabbitTemplate — для отправки сообщений (producer) и получения обратно;
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setDefaultReceiveQueue("query-example-6");
        rabbitTemplate.setReplyTimeout(60 * 1000);
        //no reply to - we use direct-reply-to
        return rabbitTemplate;
    }

    @Bean
    public Queue myQueue() {
        return new Queue("query-example-6");
    }
}
