package com.globalshopper.GlobalShopper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecretkey;
    private String secretKey;

    private final long accessTokenValidity = 15L * 60L * 1000L ;
    private final long refreshTokenValidity = 30L * 24L * 60L * 60L * 1000L;
}
