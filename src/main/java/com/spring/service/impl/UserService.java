package com.spring.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.UserEntity;
import com.spring.repository.UserRepository;
import com.spring.service.IUserSevice;

@Service
public class UserService implements IUserSevice {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserEntity findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}
	@Override
	public boolean existsByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.existsByUserName(userName);
	}
	@Override
	public boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}
	@Override
	public UserEntity saveOrUpdate(UserEntity user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	
}
