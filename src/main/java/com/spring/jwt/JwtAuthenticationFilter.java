package com.spring.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.service.impl.UserInfoService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserInfoService userInfoService;

	// lấy token từ request header sau đó kiểu tra token nếu k rỗng mà chuỗi bắt đầu
	// là "Bearer " thì cắt chuỗi và chỉ trả về token của nó nếu không có thì return
	// null
	public String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			String jwt = getTokenFromRequest(request);
			// check jwt k rỗng và validate jwt k lỗi thì get userdetails từ jwt
			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				String userName= jwtTokenProvider.getUserNameFromToken(jwt);
				UserDetails userDetails = userInfoService.loadUserByUsername(userName);
				// set userdetail vào trong spring sercurity
				if (userDetails != null) {
					// Neu nguoi dung hop le set thong tin cho security context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

			}
		} catch (Exception e) {
			log.error("fail on set user authentication", e);
		}
		filterChain.doFilter(request, response);// bộ lọc filterChain có mục đích xác thực và ủy quyền
	}

}
