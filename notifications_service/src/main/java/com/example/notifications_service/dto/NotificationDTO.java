package com.example.notifications_service.dto;

import com.example.notifications_service.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private String content;
    private LocalDate publicationTime;
    private Long userId;
    private boolean notified;
    private String notificationType;
    private List<User> subscribers;

}
