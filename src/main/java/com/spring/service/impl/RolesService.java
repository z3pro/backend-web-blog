package com.spring.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.Erole;
import com.spring.entity.Roles;
import com.spring.repository.RolesRepository;
import com.spring.service.IRoles;

@Service
public class RolesService implements IRoles {
	@Autowired
	private RolesRepository rolesRepository;

	@Override
	public Optional<Roles> findByRoleName(Erole roleName) {
		return rolesRepository.findByRole(roleName);
	}

}
