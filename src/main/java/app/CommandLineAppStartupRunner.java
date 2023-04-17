package app;

import app.model.User;
import app.model.UserRole;
import app.repository.UserRepository;
import app.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    final UserRepository userRepository;
    final UserService userService;

    public CommandLineAppStartupRunner(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        User admin = new User("ADMIN", "ADMIN", "ADMIN", "123456");
        admin.setRole(UserRole.ADMIN);
        User owner1 = new User("20000000", "Martin", "Garabal", "123456");
        owner1.setRole(UserRole.OWNER);
        User employee1 = new User("40000000", "Miguel", "Granados", "1234");
        employee1.setRole(UserRole.EMPLOYEE);
        this.userRepository.save(admin);
        this.userRepository.save(owner1);
        this.userRepository.save(employee1);
    }
}
