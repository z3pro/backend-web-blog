package com.spring.service;

import com.spring.entity.UserEntity;

public interface IUserSevice {
    UserEntity findByUserName(String userName);

    UserEntity findByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsByEmail(String email);

    UserEntity saveOrUpdate(UserEntity user);
}
