package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.UserEntity;
import com.spring.entity.VerificationEntity;

public interface VerificationRepository extends JpaRepository<VerificationEntity, Long> {
    VerificationEntity findByUser(UserEntity user);
}
