package com.spring.dto;

import java.util.Set;

import com.spring.entity.CategoryEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AticlesDTO extends AbstractDTO {
	private Long id;
	private String title;
	private String content;
	private String img;
	private String slug;
	private Set<UserInfoDTO> userLike;
	private UserInfoDTO user;
	private Category_AticlesDTO category;
}