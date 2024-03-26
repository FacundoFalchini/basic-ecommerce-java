package com.mybasicecommerce.mybasicecommercejava.tokenProvider;



import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

@Component
public class JwtTokenProvider {

    private static final String SECRET = "thisiswhitneyhouston";
    private static final long EXPIRATION_TIME = 3600000; // 1 hora

    public String createToken(String userId) {
        System.out.println(userId);
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
            //System.out.println(expirationDate);
            return JWT.create()
                    .withSubject(userId)
                    .withExpiresAt(expirationDate)
                    .withClaim("userId", userId)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            // Error al crear el token
            exception.printStackTrace();
            return null;
        }
    }
    
}
