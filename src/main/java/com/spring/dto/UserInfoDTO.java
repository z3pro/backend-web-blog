package com.spring.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
	private UUID id;
	private String userName;
	private String img;
	private String email;
}
