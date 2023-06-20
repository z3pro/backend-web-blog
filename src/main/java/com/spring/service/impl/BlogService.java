package com.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.dto.BlogDTO;
import com.spring.entity.BlogEntity;
import com.spring.entity.UserEntity;
import com.spring.repository.BlogRepository;
import com.spring.repository.UserRepository;
import com.spring.service.IBlogService;

@Service
public class BlogService implements IBlogService {
	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<BlogDTO> findAll() {
		List<BlogDTO> result = new ArrayList<>();
		List<BlogEntity> listBlog = blogRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
		for (BlogEntity item : listBlog) {
			result.add(modelMapper.map(item, BlogDTO.class));
		}
		return result;
	}

	@Override
	public BlogDTO findById(Long id) {

		return modelMapper.map(blogRepository.findById(id).get(), BlogDTO.class);
	}

	@Override
	public BlogDTO save(BlogDTO blogDTO) {
		BlogEntity blogEntity ;
		if (blogDTO.getId() != null) {
			BlogEntity blogOld = blogRepository.findById(blogDTO.getId()).get();
			if (blogDTO.getTitle() != null) {
				blogOld.setTitle(blogDTO.getTitle());
			}
			if (blogDTO.getContent() != null) {
				blogOld.setContent(blogDTO.getContent());
			}
			if (blogDTO.getSlug() != null) {
				blogOld.setSlug(blogDTO.getSlug());
			}
			if (blogDTO.getImg() != null) {
				blogOld.setImg(blogDTO.getImg());
			}
			blogEntity = blogOld;
		} else {
			blogEntity = modelMapper.map(blogDTO, BlogEntity.class);
		}

		return modelMapper.map(blogRepository.save(blogEntity), BlogDTO.class);
	}

	@Override
	public BlogDTO addUserLikeToBlog(Long blogId, UUID userId) {
		BlogEntity blog = blogRepository.findById(blogId).get();
		UserEntity user = userRepository.findById(userId).get();
		blog.addUserLike(user);
		return modelMapper.map(blogRepository.save(blog), BlogDTO.class);
	}

	@Override
	public BlogDTO removeUserLikeToBlog(Long blogId, UUID userId) {
		BlogEntity blog = blogRepository.findById(blogId).get();
		UserEntity user = userRepository.findById(userId).get();
		blog.removeUserLike(user);
		return modelMapper.map(blogRepository.save(blog), BlogDTO.class);
	}

	@Override
	public void delete(Long id) {
		blogRepository.deleteById(id);
	}

}
