package app.security.auth;

import app.model.dto.TokenForm;
import app.security.config.JwtService;
import app.security.model.response.LoginResponse;
import app.security.model.response.TokenResponse;
import app.model.dto.UserLoginForm;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public AuthController(final UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginForm user) {
        String token = this.userService.loginUser(user);
        String dni = this.jwtService.extractDni(token);
        String firstName = this.userService.getUserByDni(this.jwtService.extractDni(token)).getFirstName();
        String lastName = this.userService.getUserByDni(this.jwtService.extractDni(token)).getLastName();
        String role = this.jwtService.extractRole(token);
        return new ResponseEntity<>(new LoginResponse(new TokenResponse(token), dni, firstName, lastName, role), HttpStatus.OK);
    }

    @PostMapping(path = "/logout", consumes = {"application/json"})
    public ResponseEntity<?> logout(@RequestBody TokenForm tokenForm){
        this.jwtService.tokens.remove(tokenForm.token());
        return new ResponseEntity<>("Token deleted" ,HttpStatus.OK);
    }
}
