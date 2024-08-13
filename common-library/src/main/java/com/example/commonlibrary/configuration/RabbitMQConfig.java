package com.example.commonlibrary.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
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

    public static final String NEW_LIKE_QUEUE = "new_like";

    public static final String NEW_COMMENT_QUEUE = "new_comment";

    public static final String ROUTING_KEY_PUBLICATIONS = "routingKey.publications";
    public static final String ROUTING_KEY_NOTIFICATIONS = "routingKey.notifications";

    public static final String ROUTING_KEY_ACTIVITY = "routingKey.activity";

    public static final String ROUTING_KEY_LIKE = "routingKey.like";
    public static final String ROUTING_KEY_COMMENT = "routingKey.comment";

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

    @Bean
    public Queue newLikeQueue() {
        return new Queue(NEW_LIKE_QUEUE);
    }

    @Bean
    public Queue newCommentQueue() {
        return new Queue(NEW_COMMENT_QUEUE);
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

    @Bean
    public Binding bindingActivityQueue(Queue activityQueue, TopicExchange exchange) {
        return BindingBuilder.bind(activityQueue).to(exchange).with(ROUTING_KEY_ACTIVITY);
    }

    @Bean
    public Binding bindingNewLike(Queue newLikeQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newLikeQueue).to(exchange).with(ROUTING_KEY_LIKE);
    }

    @Bean
    public Binding bindingNewComment(Queue newCommentQueue, TopicExchange exchange) {
        return BindingBuilder.bind(newCommentQueue).to(exchange).with(ROUTING_KEY_COMMENT);
    }

    // Конфигурация RabbitTemplate
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

}
