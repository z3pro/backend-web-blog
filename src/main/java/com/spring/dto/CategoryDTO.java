package com.spring.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends AbstractDTO {
	private Long id;
	private String title;
	private String img;
	private String slug;
	private Set<Aticles_CategoryDTO> listAticles;
}
