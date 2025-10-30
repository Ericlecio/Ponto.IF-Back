package br.edu.ifpe.pontoif.pontoif.service;

import br.edu.ifpe.pontoif.pontoif.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("api-local")
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().toString())
                    .withClaim("name", user.getName())
                    .withClaim("email", user.getEmail())
                    .withClaim("id", user.getId().toString())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("error in authenticated");
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

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
