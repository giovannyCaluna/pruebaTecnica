package com.bank.cuenta.config;

import com.bank.cuenta.async.RequestCuentaListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String M2_QUEUE = "cuenta-request-queue";
    public static final String M2_EXCHANGE = "m2-exchange";
    public static final String M2_ROUTING = "m2.routing.key";


    @Bean
    public TopicExchange m2Exchange() {
        return new TopicExchange(M2_EXCHANGE);
    }

    @Bean
    public Queue m2Queue() {
        return new Queue(M2_QUEUE);
    }


    @Bean
    public Binding binding(Queue m2Queue, TopicExchange m2Exchange) {
        return BindingBuilder.bind(m2Queue).to(m2Exchange).with(M2_ROUTING);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(M2_QUEUE);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(RequestCuentaListener receiver) {
        return new MessageListenerAdapter(receiver, "handleRequestCuentas");
    }



}
