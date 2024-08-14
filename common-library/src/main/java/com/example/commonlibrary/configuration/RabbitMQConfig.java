package com.example.commonlibrary.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static final String EXCHANGE_NAME = "social_media_exchange";

    public static final String NEW_PUBLICATIONS_QUEUE = "new_publications";

    public static final String SUBSCRIBER_NOTIFICATIONS_QUEUE = "subscriber_notifications";

    public static final String ACTIVITY_QUEUE = "activity_queue";

    public static final String ROUTING_KEY_PUBLICATIONS = "routingKey.publications";
    public static final String ROUTING_KEY_NOTIFICATIONS = "routingKey.notifications";

    public static final String ROUTING_KEY_ACTIVITY = "routingKey.activity";


    @Bean
    public TopicExchange exchange() {
        logger.info("Topic created {}", EXCHANGE_NAME);
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue newPublicationsQueue() {
        logger.info("Queue created {}", NEW_PUBLICATIONS_QUEUE);
        return new Queue(NEW_PUBLICATIONS_QUEUE);
    }

    @Bean
    public Queue subscriberNotificationsQueue() {
        logger.info("Subscriber queue created: {}", SUBSCRIBER_NOTIFICATIONS_QUEUE);
        return new Queue(SUBSCRIBER_NOTIFICATIONS_QUEUE);
    }

    @Bean
    public Queue activityQueue() {
        logger.info("Activity queue created: {}", ACTIVITY_QUEUE);
        return new Queue(ACTIVITY_QUEUE);
    }


    // Связывание очередей с обменником и установкой маршрутизации
    @Bean
    public Binding bindingNewPublications(Queue newPublicationsQueue, TopicExchange exchange) {
        logger.info("Binding publications queue: {}", newPublicationsQueue);
        return BindingBuilder.bind(newPublicationsQueue).to(exchange).with(ROUTING_KEY_PUBLICATIONS);
    }

    @Bean
    public Binding bindingSubscriberNotifications(Queue subscriberNotificationsQueue, TopicExchange exchange) {
        logger.info("Binding subscribers queue: {}", subscriberNotificationsQueue);
        return BindingBuilder.bind(subscriberNotificationsQueue).to(exchange).with(ROUTING_KEY_NOTIFICATIONS);
    }

    @Bean
    public Binding bindingActivityQueue(Queue activityQueue, TopicExchange exchange) {
        logger.info("Binding activity queue: {}", activityQueue);
        return BindingBuilder.bind(activityQueue).to(exchange).with(ROUTING_KEY_ACTIVITY);
    }

    // Конфигурация RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        logger.info("Rabbit Template created: {}", connectionFactory);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;

    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
