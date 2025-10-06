package com.plazoleta.plazoleta_service.infraestructure.driven.event.adapter.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PEDIDO_CREADO_QUEUE = "pedido.creado";
    public static final String PEDIDO_LISTO_QUEUE = "pedido.listo";
    @Bean
    public Queue pedidoCreadoQueue() {
        return new Queue(PEDIDO_CREADO_QUEUE, true);
    }

    @Bean
    public Queue pedidoListoQueue() {return new Queue(PEDIDO_LISTO_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
}
