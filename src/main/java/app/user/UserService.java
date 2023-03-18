package app.user;

import org.jetbrains.annotations.NotNull;
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

    public void addNewOwner(@RequestBody @NotNull User user) {
        Optional<User> userOptional = this.userRepository.findUserByDni(user.getDni());
        if (userOptional.isPresent())
            throw new IllegalStateException("user " + userOptional.get().getUserId() + " already exists");
        this.userRepository.save(user);
    }

    public void deleteOwner(@NotNull Integer userId) {
        if (!this.userRepository.existsById(userId))
            throw new IllegalStateException("user " + userId + " not found");
        this.userRepository.deleteById(userId);
    }
}
