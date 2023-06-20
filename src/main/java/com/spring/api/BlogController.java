package com.spring.api;

import java.util.List;
import java.util.UUID;

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

import com.spring.dto.BlogDTO;
import com.spring.payload.response.MessageResponse;
import com.spring.service.impl.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@GetMapping(value = "")
	public List<BlogDTO> showBlog() {
		return blogService.findAll();
	}
	@GetMapping("get-id/{id}")
	public ResponseEntity<?> getBlog(@PathVariable Long id) {
		BlogDTO result = blogService.findById(id);
		if (result == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found!"));
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/add")
	public BlogDTO saveBlog(@RequestBody BlogDTO blogDTO) {
		return blogService.save(blogDTO);
	}

	@PutMapping("/{id}")
	public BlogDTO putBlog(@RequestBody BlogDTO blogDTO) {
		return blogService.save(blogDTO);
	}

	@PostMapping("/add/{blogId}/to/{userId}")
	public BlogDTO addUserLike(@PathVariable Long blogId, @PathVariable UUID userId) {
		return blogService.addUserLikeToBlog(blogId, userId);
	}

	@PostMapping("/delete/{blogId}/to/{userId}")
	public BlogDTO removeUserLike(@PathVariable Long blogId, @PathVariable UUID userId) {
		return blogService.removeUserLikeToBlog(blogId, userId);
	}

	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		blogService.delete(id);
	}

}
