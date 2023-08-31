package com.spring.api;

import java.util.List;
import java.util.UUID;

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

import com.spring.dto.AticlesDTO;
import com.spring.payload.response.MessageResponse;
import com.spring.service.impl.AticlesService;

@RestController
@RequestMapping("/aticles")
public class AticlesController {
	@Autowired
	private AticlesService aticlesService;

	@GetMapping(value = {"","/"})
	public List<AticlesDTO> showAticles() {
		return aticlesService.findAll();
	}

	@GetMapping("/get-id/{id}")
	public ResponseEntity<?> getAticle(@PathVariable Long id) {
		AticlesDTO result = aticlesService.findAticlesById(id);
		if (result == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("User not found!"));
		}
		return ResponseEntity.ok(result);
	}
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/add")
	public AticlesDTO saveAticles(@RequestBody AticlesDTO aticlesDTO) {
		return aticlesService.save(aticlesDTO);
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PutMapping("/{id}")
	public AticlesDTO putAticle(@RequestBody AticlesDTO aticlesDTO) {
		return aticlesService.save(aticlesDTO);
	}

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PostMapping("/add/{userId}/to/{aticleId}")
	public AticlesDTO addUserLike(@PathVariable Long aticleId, @PathVariable UUID userId) {
		return aticlesService.addUserLikeToAticle(aticleId, userId);
	}

	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PostMapping("/delete/{userId}/to/{aticleId}")
	public AticlesDTO removeUserLike(@PathVariable Long aticleId, @PathVariable UUID userId) {
		return aticlesService.removeUserLikeToAticle(aticleId, userId);
	}

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		aticlesService.delete(id);
	}

}
