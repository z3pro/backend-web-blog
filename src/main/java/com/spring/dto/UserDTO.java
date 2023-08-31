package com.spring.dto;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.spring.entity.Roles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends AbstractDTO {
	private UUID id;
	private String userName;
	private String address;
	private Date birthDate;
	private String img;
	private String gender;
	private String email;
	private String phone;
	private Set<Roles> listRoles;
}
