package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.BlogEntity;

public interface BlogRepository extends JpaRepository<BlogEntity, Long>{

}
