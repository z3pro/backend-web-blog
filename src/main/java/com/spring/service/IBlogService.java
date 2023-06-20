package com.spring.service;

import java.util.List;
import java.util.UUID;

import com.spring.dto.BlogDTO;

public interface IBlogService {
	List<BlogDTO> findAll();
	BlogDTO findById(Long id);
	BlogDTO save(BlogDTO blogDTO);
	BlogDTO addUserLikeToBlog(Long blogId,UUID userId);
	BlogDTO removeUserLikeToBlog(Long blogId,UUID userId);
	void delete(Long id);
}
