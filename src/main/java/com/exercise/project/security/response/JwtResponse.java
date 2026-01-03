package com.exercise.project.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private String token;

    private String type;

    private String refreshToken;

    private UserInfoResponse userInfoResponse;

}
