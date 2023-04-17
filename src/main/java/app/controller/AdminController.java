package app.controller;

import app.model.User;
import app.model.form.UserEditForm;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/user/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/add-owner", consumes = {"application/json"})
    public ResponseEntity<?> createOwner(@RequestBody final User user) {
        this.userService.saveOwner(user);
        return new ResponseEntity<>("owner " + user.getUserId() + " created", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete-owner/{userId}")
    public ResponseEntity<?> deleteOwner(@PathVariable final Long userId) {
        this.userService.deleteOwner(userId);
        return new ResponseEntity<>("owner " + userId + " deleted", HttpStatus.OK);
    }

    @PutMapping(path = "/update-owner/{userId}", consumes = {"application/json"})
    public ResponseEntity<?> modifyOwner(@PathVariable final Long userId, @RequestBody final UserEditForm user) {
        this.userService.updateOwner(userId, user);
        return new ResponseEntity<>("owner " + userId + " updated", HttpStatus.OK);
    }

    @GetMapping(path = "panel-employee")
    public List<Object> getAllEmployees() {
        return this.userService.getAllEmployees();
    }

    @GetMapping(path = "panel-owner")
    public List<Object> getAllOwners() {
        return this.userService.getAllOwners();
    }
}
