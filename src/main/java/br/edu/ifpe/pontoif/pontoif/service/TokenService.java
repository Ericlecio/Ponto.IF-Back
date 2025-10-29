package br.edu.ifpe.pontoif.pontoif.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public static String extract(String token) {
        try {
            token = token.replaceFirst("Bearer", "");
            token = token.trim();
            return JWT.decode(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error in extract UUID of the token.");
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var cx = JWT.require(algorithm)
                    .withIssuer("api-local")
                    .build()
                    .verify(token)
                    .getClaims();


            return cx.get("email").asString();
        } catch (JWTVerificationException exception) {
            return  null;
        }
    }

    public String extractID(String authHeader) {
        try {
            String token = authHeader.substring(7);
            return JWT.decode(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Error in extract UUID of the token.");
        }
    }

}
