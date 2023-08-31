package com.spring.service.impl;

import com.spring.api.User;
import com.spring.entity.RefreshTokenEntity;
import com.spring.entity.UserEntity;
import com.spring.repository.RefreshTokenRepository;
import com.spring.repository.UserRepository;
import com.spring.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public RefreshTokenEntity findByToken(String token) {
        return refreshTokenRepository.findByToken(token).get();
    }

    @Override
    public RefreshTokenEntity findByUser(String name) {
        UserEntity user = userRepository.findByUserName(name);
        if (user != null) {
            RefreshTokenEntity refreshToken;
            try {
                refreshToken = refreshTokenRepository.findByUserEntity(user).get();
            } catch (Exception e){
                refreshToken = null;
            };
           return (refreshToken != null) ? refreshToken : null;
        } else {
            return null;
        }
    }

    @Override
    public RefreshTokenEntity createRefreshToken(String userName) {
        if(userName != null) {
            RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                    .userEntity(userRepository.findByUserName(userName))
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis((long) 2592e9))// 3 day
                    .build();
            return refreshTokenRepository.save(refreshToken);
        } else {
            return null;
        }
    }

    @Override
    public RefreshTokenEntity updateRefreshToken(String tokenOld) {
        if (tokenOld != null) {
            RefreshTokenEntity refreshTokenOld = refreshTokenRepository.findByToken(tokenOld).get();
            RefreshTokenEntity refreshTokenNew = RefreshTokenEntity.builder()
                    .userEntity(refreshTokenOld.getUserEntity())
                    .token(UUID.randomUUID().toString())
                    .expiryDate(refreshTokenOld.getExpiryDate())
                    .build();
            refreshTokenRepository.delete(refreshTokenOld);
            refreshTokenRepository.save(refreshTokenNew);
            return refreshTokenNew;
        }else {
            return null;
        }
    }


    @Override
    public void deleteRefreshToken(RefreshTokenEntity refreshTokenEntity) {
        refreshTokenRepository.delete(refreshTokenEntity);
    }
}
