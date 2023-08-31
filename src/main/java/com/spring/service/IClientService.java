package com.spring.service;

import com.spring.payload.request.SignupRequest;

public interface IClientService {
    Boolean create(SignupRequest signupRequest);
}
