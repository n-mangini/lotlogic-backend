package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/api/user/add", consumes = {"application/json"})
    public void addOwner(@RequestBody final User user) {
        this.userService.addNewOwner(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/api/user/delete/{userId}", consumes = {"application/json"})
    public void deleteOwner(@PathVariable final Long userId) {
        this.userService.deleteOwner(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/api/user/update/{userId}", consumes = {"application/json"})
    public void modifyOwner(@RequestBody final User user, @PathVariable final Long userId) {
        this.userService.modifyOwner(user.getDni(), user.getFirstName(), user.getLastName(), user.getPassword(), userId);
    }
}