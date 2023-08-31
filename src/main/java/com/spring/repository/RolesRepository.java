package com.spring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.entity.Erole;
import com.spring.entity.Roles;

public interface RolesRepository extends JpaRepository<Roles, Integer> {
	Optional<Roles> findByRole(Erole role);
}
