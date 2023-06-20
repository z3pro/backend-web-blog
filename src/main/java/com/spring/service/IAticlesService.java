package com.spring.service;

import java.util.List;
import java.util.UUID;

import com.spring.dto.AticlesDTO;
import com.spring.entity.AticlesEntity;

public interface  IAticlesService   {
	List<AticlesDTO> findAll();
	AticlesDTO findAticlesById(Long id);
	AticlesDTO save(AticlesDTO aticlesDTO);
	AticlesDTO addUserLikeToAticle(Long aticleId,UUID userId);
	AticlesDTO removeUserLikeToAticle(Long aticleId,UUID userId);
	void delete(Long id);
}
