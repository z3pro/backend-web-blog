package com.spring.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.entity.UserEntity;
import com.spring.entity.VerificationEntity;
import com.spring.repository.VerificationRepository;
import com.spring.service.IVerificationService;

@Service
public class VerificationService implements IVerificationService {
    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public VerificationEntity findByUser(UserEntity user) {
        return verificationRepository.findByUser(user);
    }

    @Override
    public void saveVerification(VerificationEntity verification) {
        verificationRepository.save(verification);
    }

    @Override
    public void deleteVerification(VerificationEntity verification) {
        verificationRepository.delete(verification);
    }

}
