package com.example.publications_service.controller;

import com.example.publications_service.entity.Publication;
import com.example.publications_service.entity.User;
import com.example.publications_service.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publications")
@AllArgsConstructor
public class PublicationsController {

    private final PublicationService publicationService;


    @PostMapping("/new")
    public ResponseEntity<Publication> createPublication(@RequestBody Publication publication) {
        return ResponseEntity.ok(publicationService.createPublication(publication));
    }

    @GetMapping("/get_user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(publicationService.getUserByUserId(userId));
    }

    @GetMapping("/get_publication/{publicationId}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable Long publicationId) {
        return ResponseEntity.ok(publicationService.getPublication(publicationId));
    }

}
