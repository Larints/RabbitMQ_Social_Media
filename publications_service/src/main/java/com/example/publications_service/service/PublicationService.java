package com.example.publications_service.service;

import com.example.commonlibrary.configuration.RabbitMQConfig;
import com.example.publications_service.dto.NotificationDTO;
import com.example.publications_service.entity.Notification;
import com.example.publications_service.entity.Publication;
import com.example.publications_service.entity.User;
import com.example.publications_service.repository.NotificationRepository;
import com.example.publications_service.repository.PublicationRepository;
import com.example.publications_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {

    private final static Logger logger = LoggerFactory.getLogger(PublicationService.class);

    private final UserRepository userRepository;

    private final PublicationRepository publicationRepository;

    private final NotificationRepository notificationRepository;

    private final RabbitTemplate rabbitTemplate;


    public Publication createPublication(Publication publication) {
        // Сохраняем публикацию в базе данных
        publication = publicationRepository.save(publication);
        logger.info("Publication saved: {}", publication);
        // Получаем пользователя и обновляем его список публикаций
        User user = publication.getUser();
        List<Publication> publications = user.getPublications();
        if (publications == null) {
            publications = new ArrayList<>();
        }
        publications.add(publication);
        user.setPublications(publications);
        userRepository.save(user);
        logger.info("User saved: {}", user);
        // Создаем DTO и отправляем уведомление
        NotificationDTO notificationDTO = NotificationDTO.builder()
                .id(publication.getId())
                .content(publication.getContent())
                .publicationTime(publication.getPublicationTime())
                .userId(user.getId())
                .notified(false)
                .notificationType(publication.getNotificationType().name())
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_PUBLICATIONS, notificationDTO);
        logger.info("NotificationDTO send to queue: {}", RabbitMQConfig.NEW_PUBLICATIONS_QUEUE);
        return publication;
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public Publication getPublication(Long publicationId) {
        return publicationRepository.findById(publicationId).orElseThrow();
    }

    public List<Notification> getUserNotifications(Long userID) {
        return notificationRepository.findByUserId(userID);
    }

//    @RabbitListener(queues = RabbitMQConfig.ACTIVITY_QUEUE)
//    public void receiveNewNotification(NotificationDTO notificationDTO) {
//        logger.info("NotificationDTO received from queue: {}", notificationDTO);
//        User user = userRepository.findById(notificationDTO.getUserId()).orElseThrow();
//        user.setSubscriptions(notificationDTO.getSubscribers());
//        logger.info("Used subscriptions updated", user);
//    }
}
