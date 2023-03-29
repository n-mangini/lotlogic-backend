package app.controller;

import app.model.User;
import app.model.form.UserEditForm;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    //role: admin
    @PostMapping(path = "/add-owner", consumes = {"application/json"})
    public ResponseEntity addOwner(@RequestBody final User user) {
        this.userService.addNewOwner(user);
        return new ResponseEntity("owner " + user.getUserId() + " created", HttpStatus.CREATED);
    }

    //role: owner
    @PostMapping(path = "/add-employee", consumes = {"application/json"})
    public ResponseEntity addEmployee(@RequestBody final User user) {
        this.userService.addNewEmployee(user);
        return new ResponseEntity("employee " + user.getUserId() + " created", HttpStatus.CREATED);
    }

    //role: admin
    @DeleteMapping(path = "/delete-owner/{userId}")
    public ResponseEntity deleteOwner(@PathVariable final Long userId) {
        this.userService.deleteOwner(userId);
        return new ResponseEntity("owner " + userId + " deleted", HttpStatus.OK);
    }

    //role: owner
    @DeleteMapping(path = "/delete-employee/{userId}")
    public ResponseEntity deleteEmployee(@PathVariable final Long userId) {
        this.userService.deleteEmployee(userId);
        return new ResponseEntity("employee " + userId + " deleted", HttpStatus.OK);
    }

    //role: admin
    @PutMapping(path = "/update-owner/{userId}", consumes = {"application/json"})
    public ResponseEntity modifyOwner(@PathVariable final Long userId, @RequestBody final UserEditForm user) {
        this.userService.modifyOwner(userId, user);
        return new ResponseEntity("owner " + userId + " updated", HttpStatus.OK);
    }

    //role: owner
    @PutMapping(path = "/update-employee/{userId}", consumes = {"application/json"})
    public ResponseEntity modifyEmployee(@PathVariable final Long userId, @RequestBody final UserEditForm user) {
        this.userService.modifyEmployee(userId, user);
        return new ResponseEntity("employee " + userId + " updated", HttpStatus.OK);
    }
}
