package com.example.commonlibrary.configuration;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "social_media_exchange";

    public static final String NEW_PUBLICATIONS_QUEUE = "new_publications";

    public static final String SUBSCRIBER_NOTIFICATIONS_QUEUE = "subscriber_notifications";

    public static final String ACTIVITY_QUEUE = "activity_queue";
    public static final String ROUTING_KEY_PUBLICATIONS = "routingKey.publications";
    public static final String ROUTING_KEY_NOTIFICATIONS = "routingKey.notifications";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue newPublicationsQueue() {
        return new Queue(NEW_PUBLICATIONS_QUEUE);
    }

    @Bean
    public Queue subscriberNotificationsQueue() {
        return new Queue(SUBSCRIBER_NOTIFICATIONS_QUEUE);
    }

    @Bean
    public Queue activityQueue() {
        return new Queue(ACTIVITY_QUEUE);
    }

    // Связывание очередей с обменником и установкой маршрутизации
    @Bean
    public Binding bindingNewPublications(Queue newPublicationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newPublicationsQueue).to(exchange).with(ROUTING_KEY_PUBLICATIONS);
    }

    @Bean
    public Binding bindingSubscriberNotifications(Queue subscriberNotificationsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(subscriberNotificationsQueue).to(exchange).with(ROUTING_KEY_NOTIFICATIONS);
    }

    // Конфигурация RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}
