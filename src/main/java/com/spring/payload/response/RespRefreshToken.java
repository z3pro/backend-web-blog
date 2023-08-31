package com.spring.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RespRefreshToken {
    private String refresh_token;
    private String access_token;
}
