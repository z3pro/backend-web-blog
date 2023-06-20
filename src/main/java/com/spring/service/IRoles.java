package com.spring.service;

import java.util.Optional;

import com.spring.entity.Erole;
import com.spring.entity.Roles;

public interface IRoles{
	Optional<Roles> findByRoleName(Erole roleName); 
}
