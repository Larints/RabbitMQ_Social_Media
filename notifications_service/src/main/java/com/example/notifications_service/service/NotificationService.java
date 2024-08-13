package com.example.notifications_service.service;

import com.example.commonlibrary.configuration.RabbitMQConfig;
import com.example.notifications_service.dto.NotificationDTO;
import com.example.notifications_service.entity.Notification;
import com.example.notifications_service.entity.User;
import com.example.notifications_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private final static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final RabbitTemplate rabbitTemplate;

    private final UserRepository userRepository;

    @RabbitListener(queues = RabbitMQConfig.SUBSCRIBER_NOTIFICATIONS_QUEUE)
    public void noticeAllUsers(NotificationDTO notificationDTO) {
        logger.info("NotificationDTO from SUBSCRIBER_NOTIFICATIONS_QUEUE received: {}", notificationDTO);
        User user = userRepository.findById(notificationDTO.getUserId()).orElseThrow();
        List<User> subscribers = notificationDTO.getSubscribers();
        Notification notification = Notification.builder()
                .content(notificationDTO.getContent())
                .notificationTime(notificationDTO.getPublicationTime())
                .notified(true)
                .user(user)
                .notificationType(notificationDTO.getNotificationType())
                .build();
        for (User subscriber : subscribers) {
            subscriber.getNotifications().add(notification);
        }
        notificationDTO.setSubscribers(subscribers);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ACTIVITY_QUEUE, notificationDTO);
        logger.info("NotificationDTO send to received: {}", RabbitMQConfig.ACTIVITY_QUEUE);
    }

}
