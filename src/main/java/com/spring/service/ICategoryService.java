package com.spring.service;

import java.util.List;
import java.util.UUID;

import com.spring.dto.CategoryDTO;

public interface ICategoryService {
	List<CategoryDTO> findAll();
	CategoryDTO findByidCategory(Long id);
	CategoryDTO saveCategory(CategoryDTO category);
	CategoryDTO addAticleToCategory(Long aticleId,Long categoryId);
	CategoryDTO removeAticleToCategory(Long aticleId,Long categoryId);
	void deleteCategory(Long id);
}
