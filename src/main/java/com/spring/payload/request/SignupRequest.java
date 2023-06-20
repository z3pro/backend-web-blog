package com.spring.payload.request;

import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
	private String userName;
    private String password;
    private String email;
    private String phone;
    private String address;
    private boolean userStatus;
    private Set<String> listRoles;
    private Date birthDate;
    private String gender;
}
