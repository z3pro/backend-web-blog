package com.spring.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.UserEntity;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	UserEntity findByUserName(String userName);

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);

	UserEntity findByEmail(String username);
}
