package com.spring.api;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.CategoryDTO;
import com.spring.payload.response.MessageResponse;
import com.spring.service.impl.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = {"","/"})
	public ResponseEntity<List<CategoryDTO>> findAllCategory() {
		List<CategoryDTO> result = categoryService.findAll();
		return ResponseEntity.ok(result);

	}

	@GetMapping("/get-id/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
		CategoryDTO result = categoryService.findByidCategory(id);
		if (result == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Category not found!"));
		}
		return ResponseEntity.ok(result);
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public ResponseEntity<CategoryDTO> postCategory(@RequestBody CategoryDTO categoryEntity) {
		CategoryDTO result = categoryService.saveCategory(categoryEntity);
		return ResponseEntity.ok(result);
	}
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> putCategory(@RequestBody CategoryDTO categoryEntity) {
		CategoryDTO result = categoryService.saveCategory(categoryEntity);
		return ResponseEntity.ok(result);
	}
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<MessageResponse> deleteCategory(@PathVariable Long id) {
		try {
			System.out.println(id + "id");
			categoryService.deleteCategory(id);
			return ResponseEntity.ok(new MessageResponse("Delete category success!"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Delete category to fail!"));
		}
	}
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/add/{articlesId}/to/{categoryId}")
	public ResponseEntity<CategoryDTO> addArticlesToCategory(@PathVariable Long articlesId, @PathVariable Long categoryId) {
		CategoryDTO result = categoryService.addAticleToCategory(articlesId, categoryId);
		return ResponseEntity.ok(result);
	}
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/delete/{articlesId}/to/{categoryId}")
	public ResponseEntity<CategoryDTO> removeArticlesToCategory(@PathVariable Long articlesId, @PathVariable Long categoryId) {
		CategoryDTO result = categoryService.removeAticleToCategory(articlesId, categoryId);
		return ResponseEntity.ok(result);
	}
}
