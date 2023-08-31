package com.spring.service;

import com.spring.entity.UserEntity;
import com.spring.entity.VerificationEntity;

public interface IVerificationService {
    VerificationEntity findByUser(UserEntity user);

    void saveVerification(VerificationEntity verification);

    void deleteVerification(VerificationEntity verification);
}
