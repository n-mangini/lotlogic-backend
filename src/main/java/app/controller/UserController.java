package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/api/user/add")
    public void addOwner(@RequestBody final User user) {
        this.userService.addNewOwner(user);
    }

    @DeleteMapping(path = "/api/user/delete/{userId}")
    public void deleteOwner(@PathVariable final Long userId) {
        this.userService.deleteOwner(userId);
    }

    @PutMapping(path = "/api/user/update/{userId}")
    public void modifyOwner(@RequestBody final User user, @PathVariable final Long userId) {
        this.userService.modifyOwner(user.getDni(), user.getFirstName(), user.getLastName(), user.getPassword(), userId);
    }
}
