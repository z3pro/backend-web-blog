package com.spring.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	@GetMapping
	public ResponseEntity<?> findAllCategory() {
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
	@PostMapping("/add")
	public ResponseEntity<?> postCategory(@RequestBody CategoryDTO categoryEntity) {
		CategoryDTO result = categoryService.saveCategory(categoryEntity);
		return ResponseEntity.ok(result);
	}
	@PutMapping("/{id}")
	public ResponseEntity<?> putCategory(@RequestBody CategoryDTO categoryEntity) {
		CategoryDTO result = categoryService.saveCategory(categoryEntity);
		return ResponseEntity.ok(result);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletCategory(@PathVariable Long id) {
		try {
			categoryService.deleteCategory(id);
			return ResponseEntity.ok(new MessageResponse("Delete category success!"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse("Delete category to fail!"));
		}
	}
	@PostMapping("/add/{aticlesId}/to/{categoryId}")
	public ResponseEntity<?> addAticlestoCategory(@PathVariable Long aticlesId,@PathVariable Long categoryId) {
		CategoryDTO result = categoryService.addAticleToCategory(aticlesId, categoryId);
		return ResponseEntity.ok(result);
	}
	@PostMapping("/delete/{aticlesId}/to/{categoryId}")
	public ResponseEntity<?> removeAticlestoCategory(@PathVariable Long aticlesId,@PathVariable Long categoryId) {
		CategoryDTO result = categoryService.removeAticleToCategory(aticlesId, categoryId);
		return ResponseEntity.ok(result);
	}
}
 