package com.example.publications_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "publications")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publication {

    @Column(name = "publication_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDate publicationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private boolean notified;

    private NotificationType notificationType;

    public static enum NotificationType {
        NEW_PUBLICATION, NEW_LIKE, NEW_COMMENT
    }
}
