package com.spring.service;

import com.spring.entity.RefreshTokenEntity;
import com.spring.entity.UserEntity;

import java.util.Optional;

public interface IRefreshTokenService {
    RefreshTokenEntity findByToken (String token);
    RefreshTokenEntity findByUser(String name);
    RefreshTokenEntity createRefreshToken(String userName);
    RefreshTokenEntity updateRefreshToken(String tokenOld);
    void deleteRefreshToken(RefreshTokenEntity refreshTokenEntity);
}
