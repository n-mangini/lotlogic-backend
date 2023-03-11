package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void addNewUser(@RequestBody User user) {
        Optional<User> userOptional = userRepository.findUserByDni(user.getDni());
        if (userOptional.isPresent()) throw new IllegalStateException("El usuario ya existe");
        userRepository.save(user);
    }
}
