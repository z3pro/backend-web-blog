package com.spring.dto;

import com.spring.entity.Roles;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
public class CustomUserDetailDTO {
    private UUID id;
    private String userName;
    private String address;
    private Date birthDate;
    private String img;
    private String gender;
    private String email;
    private String phone;
    private Collection<? extends GrantedAuthority> authorities;
}
