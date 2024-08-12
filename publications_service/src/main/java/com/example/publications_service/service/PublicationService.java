package com.example.publications_service.service;

import com.example.commonlibrary.configuration.RabbitMQConfig;
import com.example.publications_service.dto.PublicationDTO;
import com.example.publications_service.entity.Publication;
import com.example.publications_service.entity.User;
import com.example.publications_service.repository.PublicationRepository;
import com.example.publications_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PublicationService {

    private final UserRepository userRepository;

    private final PublicationRepository publicationRepository;

    private final RabbitTemplate rabbitTemplate;

    public Publication createPublication(Publication publication) {
        // Сохраняем публикацию в базе данных
        publication = publicationRepository.save(publication);

        // Получаем пользователя и обновляем его список публикаций
        User user = publication.getUser();
        List<Publication> publications = user.getPublications();
        if (publications == null) {
            publications = new ArrayList<>();
        }
        publications.add(publication);
        user.setPublications(publications);
        userRepository.save(user);

        // Создаем DTO и отправляем уведомление
        PublicationDTO publicationDTO = PublicationDTO.builder()
                .id(publication.getId())
                .content(publication.getContent())
                .publicationTime(publication.getPublicationTime())
                .userId(user.getId())
                .notified(false)
                .build();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_PUBLICATIONS, publicationDTO);

        return publication;
    }
}
