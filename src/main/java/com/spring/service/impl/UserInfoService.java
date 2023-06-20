package com.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.spring.entity.UserEntity;
import com.spring.repository.UserRepository;
import com.spring.security.CustomUserDetails;

@Component
public class UserInfoService implements UserDetailsService {
	@Autowired
	private  UserRepository userRepository;
	@Override // Phương thức này lấy user từ database thông qua username sau đó map qua
				// userdetails và trả về userdetails
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByUserName(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return CustomUserDetails.mapUserToCustomUserDetails(userEntity);
	}


}
