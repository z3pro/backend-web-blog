package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.AticlesEntity;

public interface AticlesRepository extends JpaRepository<AticlesEntity, Long> {

}
