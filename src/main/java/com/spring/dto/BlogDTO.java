package com.spring.dto;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogDTO extends AbstractDTO {
	private Long id;
	private String title;
	private String content;
	private String img;
	private String slug;
	private UserInfoDTO auth;
	private Set<UserInfoDTO> userLike;
}
