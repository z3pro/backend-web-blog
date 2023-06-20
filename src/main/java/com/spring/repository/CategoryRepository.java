package com.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>{

}
