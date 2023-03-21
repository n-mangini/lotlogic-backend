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

    @DeleteMapping(path = "/api/user/{userId}")
    public void deleteOwner(@PathVariable Integer userId) {
        this.userService.deleteOwner(userId);
    }

    @PutMapping(path = "/api/user/update/{userId}")
    public void modifyOwner(@RequestBody User user, @PathVariable Integer userId) {
        this.userService.modifyOwner(user.getDni(), user.getName(), user.getSurname(), user.getPassword(), userId);
    }
}
