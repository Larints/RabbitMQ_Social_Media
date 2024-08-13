package com.example.subscriptions_service.repository;

import com.example.subscriptions_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
