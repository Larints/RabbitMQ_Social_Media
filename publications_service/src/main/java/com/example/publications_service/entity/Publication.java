package com.example.publications_service.entity;

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
    private Long id;

    private String content;

    private LocalDate publicationTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean notified;

}
