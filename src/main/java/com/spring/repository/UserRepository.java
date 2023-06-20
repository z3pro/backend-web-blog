package com.spring.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	UserEntity findByUserName(String userName);

	boolean existsByUserName(String userName);

	boolean existsByEmail(String email);

	Object findByEmail(String username);
}
