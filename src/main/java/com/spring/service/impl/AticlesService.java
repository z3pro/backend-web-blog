package com.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dto.AticlesDTO;
import com.spring.entity.AticlesEntity;
import com.spring.entity.UserEntity;
import com.spring.repository.AticlesRepository;
import com.spring.repository.UserRepository;
import com.spring.service.IAticlesService;

@Service
public class AticlesService implements IAticlesService {
	@Autowired
	private AticlesRepository aticlesRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<AticlesDTO> findAll() {
		List<AticlesDTO> result = new ArrayList<>();
		List<AticlesEntity> listAticlesEntity = aticlesRepository.findAll();
		for (AticlesEntity item : listAticlesEntity) {
			AticlesDTO aticlesDTO = modelMapper.map(item, AticlesDTO.class);
			result.add(aticlesDTO);
		}
		return result;

	}

	@Override
	public AticlesDTO findAticlesById(Long id) {
		try {
			return modelMapper.map(aticlesRepository.findById(id).get(), AticlesDTO.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public AticlesDTO save(AticlesDTO aticlesDTO) {
		AticlesEntity aticles;
		if (aticlesDTO.getId() != null) {
			AticlesEntity aticlesOld = aticlesRepository.findById(aticlesDTO.getId()).get();
			if (aticlesDTO.getTitle() != null) {
				aticlesOld.setTitle(aticlesDTO.getTitle());
			}
			if (aticlesDTO.getContent() != null) {
				aticlesOld.setContent(aticlesDTO.getContent());
			}
			if (aticlesDTO.getSlug() != null) {
				aticlesOld.setSlug(aticlesDTO.getSlug());
			}
			if (aticlesDTO.getImg() != null) {
				aticlesOld.setImg(aticlesDTO.getImg());
			}
			aticles = aticlesOld;
		} else {
			aticles = modelMapper.map(aticlesDTO, AticlesEntity.class);
		}
		AticlesEntity aticlesEntity = aticlesRepository.save(aticles);
		AticlesDTO result = modelMapper.map(aticlesEntity, AticlesDTO.class);
		return result;

	}

	@Override
	public AticlesDTO addUserLikeToAticle(Long aticleId, UUID userId) {
		AticlesEntity aticles = aticlesRepository.findById(aticleId).get();
		UserEntity user = userRepository.findById(userId).get();
		if (aticles.getUserLike() == null) {
			throw new RuntimeException("Aticle already has a userLike");
		}
		aticles.addUserLike(user);
		return modelMapper.map(aticlesRepository.save(aticles), AticlesDTO.class);
	}

	@Override
	public AticlesDTO removeUserLikeToAticle(Long aticleId, UUID userId) {
		AticlesEntity aticles = aticlesRepository.findById(aticleId).get();
		UserEntity user = userRepository.findById(userId).get();
		if (aticles.getUserLike() == null) {
			throw new RuntimeException("Aticle already has a userLike");
		}
		aticles.removeUserLike(user);
		return modelMapper.map(aticlesRepository.save(aticles), AticlesDTO.class);
	}

	@Override
	public void delete(Long id) {
		AticlesEntity aticlesEntity = aticlesRepository.findById(id).get();
		for (UserEntity user : aticlesEntity.getUserLike()) {
			user.removeLikedAticles(aticlesEntity);
		}
		aticlesRepository.delete(aticlesEntity);
	}

}
