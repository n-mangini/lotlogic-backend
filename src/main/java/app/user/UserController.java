package app.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/api/user/add")
    public void addOwner(@RequestBody User user) {
        if (user.getPassword().length() < 4) throw new IllegalStateException();
        this.userService.addNewOwner(user);
    }

    @DeleteMapping(path = "{userId}")
    public void deleteOwner(@PathVariable("userId") Integer userId) {
        this.userService.deleteOwner(userId);
    }
}
