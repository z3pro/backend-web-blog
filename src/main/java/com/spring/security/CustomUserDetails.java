package com.spring.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.internal.bytebuddy.asm.Advice.Return;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;
import com.spring.entity.AticlesEntity;
import com.spring.entity.BlogEntity;
import com.spring.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@SuppressWarnings("serial")
@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
	private UUID userId;
	private String username;
	private String password;
	private String address;
	private String email;
	private String phone;
	private boolean userStatus = true;
	private Date birthDate;
	private String img;
	private String gender;
	private Set<AticlesEntity> likedAticles = new HashSet<>();
	private Set<BlogEntity> listBlog = new HashSet<>();
	private Collection<? extends GrantedAuthority> authorities;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	public static CustomUserDetails mapUserToCustomUserDetails(UserEntity user) {
		// Thay cho vòng lặp for thông thường lấy các quyền từ user map qua cho
		// autorities
		List<GrantedAuthority> listAuthorities = user.getListRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());
		CustomUserDetails result = new CustomUserDetails(user.getUserId(), user.getPassword(), user.getUserName(),
				user.getAddress(), user.getEmail(), user.getPhone(), user.isUserStatus(), user.getBirthDate(),
				user.getImg(), user.getGender(), user.getLikedAticles(), user.getListBlog(), listAuthorities);
		return result;

	}

	@Override
	public String getPassword() {

		return this.username;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
