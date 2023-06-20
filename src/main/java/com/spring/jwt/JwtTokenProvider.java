package com.spring.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {
	private final String JWT_SECRET = "Weblogjdsklfjldskjfldsfjldsflkdsflfdsfdsfdsfdsk";// chuỗi secret key
	private final int JWT_EXPRIRATION = 86400000;// Lưu thời gian sống của jwt tính theo mili giây

	// Tạo chuỗi jwt từ secret và username
	public String generateToken(String userName) {
		Date now = new Date();
		Date curentDate = new Date(now.getTime() + JWT_EXPRIRATION);
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(now) // Set thới gian bắt đầu hoạt động của chuỗi jwt là thời điểm hiện tại
				.setExpiration(curentDate) //Set thời gian kết thúc của chuỗi jwt
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact(); //mã hóa chuỗi username + secret
	}
	
	//lấy username từ jwt
	public String getUserNameFromToken(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build()
	               .parseClaimsJws(token).getBody();
		//trả về username
		return claims.getSubject();
	}
	private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
	//Validate thông tin jwt
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(getSignKey()).build().parse(token);
			return true;
		} catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        }catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
		return false;
	}
}
