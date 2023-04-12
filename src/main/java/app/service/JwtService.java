package app.service;

import app.model.User;
import app.model.form.UserLoginForm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JwtService {
    public static final String SECRET_KEY = "423F4428472B4B6250655368566D597133743677397A24432646294A404D6351";

    public String generateToken(UserLoginForm user, String role) {
        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(user.getDni())
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();
        return jwtToken;
    }
}
