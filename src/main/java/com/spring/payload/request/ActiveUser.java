package com.spring.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActiveUser {
    private String email;
    private String otp;
}
