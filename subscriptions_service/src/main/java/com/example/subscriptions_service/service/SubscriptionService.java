package com.example.subscriptions_service.service;

import com.example.commonlibrary.configuration.RabbitMQConfig;
import com.example.subscriptions_service.dto.NotificationDTO;
import com.example.subscriptions_service.entity.Notification;
import com.example.subscriptions_service.entity.User;
import com.example.subscriptions_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriptionService {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    private final UserRepository userRepository;

    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.NEW_PUBLICATIONS_QUEUE)
    public void receiveNewPublication(NotificationDTO notificationDTO) {
        logger.info("NotificationDTO from queue NEW_PUBLICATIONS_QUEUE RECEIVED  {}", notificationDTO);
        User user = userRepository.findById(notificationDTO.getUserId()).orElseThrow();
        List<User> subscribers = user.getSubscriptions();
        NotificationDTO sendNext = notificationDTO;
        sendNext.setSubscribers(subscribers);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_NOTIFICATIONS, sendNext);
        logger.info("NotificationDTO send to QUEUE {}: ", RabbitMQConfig.SUBSCRIBER_NOTIFICATIONS_QUEUE);
    }
}


