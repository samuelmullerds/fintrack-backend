package com.fintrack.fintrack_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET = "fintrack-secret";

    public String generateToken(String email){

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                .sign(algorithm);
    }

    public String validateToken(String token){

        Algorithm algorithm = Algorithm.HMAC256(SECRET);

        DecodedJWT decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token);

        return decodedJWT.getSubject();
    }
}