package com.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.CategoryDTO;
import com.spring.entity.AticlesEntity;
import com.spring.entity.CategoryEntity;
import com.spring.repository.AticlesRepository;
import com.spring.repository.CategoryRepository;
import com.spring.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private AticlesRepository aticlesRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<CategoryDTO> findAll() {
		List<CategoryDTO> result = new ArrayList<>();
		List<CategoryEntity> listCategory = categoryRepository.findAll();
		for(CategoryEntity item : listCategory) {
			result.add(modelMapper.map(item,CategoryDTO.class));
		}
		return result;
	}

	@Override
	public CategoryDTO findByidCategory(Long id) {
		try {
			CategoryEntity result = categoryRepository.findById(id).get();
			return modelMapper.map(result,CategoryDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CategoryDTO saveCategory(CategoryDTO category) {
		CategoryEntity categoryEntity;
		if (category.getId() != null) {
			CategoryEntity categoryOld = categoryRepository.findById(category.getId()).get();
			if (category.getTitle() != null) {
				categoryOld.setTitle(category.getTitle());
			}
			if (category.getImg() != null) {
				categoryOld.setImg(category.getImg());
			}
			if (category.getSlug() != null) {
				categoryOld.setSlug(category.getSlug());
			}
//			if(category.getListAticles() != null) {
//				Set<AticlesEntity> list = new HashSet<>();
//				for(Aticles_CategoryDTO item: category.getListAticles()) {
//					list.add(modelMapper.map(item, AticlesEntity.class));
//				}
//				categoryOld.setListAticles(list);
//			}
			categoryEntity = categoryOld;
		} else {
			categoryEntity = modelMapper.map(category,CategoryEntity.class);
		}

		CategoryEntity result =  categoryRepository.save(categoryEntity);
		return modelMapper.map(result,CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Long id) {
		CategoryEntity category= categoryRepository.findById(id).get();
		for(AticlesEntity item: category.getListAticles()) {
			item.setCategory(null);
			aticlesRepository.save(item);
		}
		category.setListAticles(null);
		categoryRepository.delete(category);
	}

	@Override
	public CategoryDTO addAticleToCategory(Long aticleId, Long categoryId) {
		AticlesEntity aticles= aticlesRepository.findById(aticleId).get();
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		aticles.setCategory(category);
		aticlesRepository.save(aticles);
		CategoryEntity result = categoryRepository.findById(categoryId).get();
		return modelMapper.map(result,CategoryDTO.class);
	}

	@Override
	public CategoryDTO removeAticleToCategory(Long aticleId, Long categoryId) {
		AticlesEntity aticles= aticlesRepository.findById(aticleId).get();
		aticles.setCategory(null);
		aticlesRepository.save(aticles);
		CategoryEntity result = categoryRepository.findById(categoryId).get();
		return modelMapper.map(result,CategoryDTO.class);
	}

}
