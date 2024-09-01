package com.bank.cliente.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String M1_REPLY_QUEUE = "cliente-reply-queue";
    public static final String EXCHANGE_NAME = "m2-exchange";
    public static final String M2_ROUTING = "m2.routing.key";
    public static final Integer TIMEOUT = 10000;

    @Bean
    public TopicExchange m2Exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue replyQueue() {
        return new Queue(M1_REPLY_QUEUE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange(EXCHANGE_NAME);
        template.setRoutingKey(M2_ROUTING);
        template.setReplyAddress(M1_REPLY_QUEUE);
        template.setReplyTimeout(TIMEOUT);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyListenerContainer(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(M1_REPLY_QUEUE);
        container.setMessageListener(rabbitTemplate);
        return container;
    }

}
