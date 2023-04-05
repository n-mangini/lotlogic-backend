package app.service;

import app.model.form.UserLoginForm;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class JwtService {
    public static final String SECRET_KEY = "423F4428472B4B6250655368566D597133743677397A24432646294A404D6351";

/*    public Map<String, String> generateToken(UserLoginForm user) {
        String jwtToken;
        jwtToken = Jwts.builder().setSubject(user.getDni()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        return jwtTokenGen;
    }*/

    public String generateToken(UserLoginForm user) {
        String jwtToken;
        jwtToken = Jwts.builder()
                .setSubject(user.getDni())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        return jwtToken;
    }
}
