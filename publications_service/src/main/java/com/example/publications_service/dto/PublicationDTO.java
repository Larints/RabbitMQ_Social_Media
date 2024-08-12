package com.example.publications_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicationDTO {

    private Long id;
    private String content;
    private LocalDate publicationTime;
    private Long userId;
    private boolean notified;
}
