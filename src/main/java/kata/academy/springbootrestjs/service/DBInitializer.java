package kata.academy.springbootrestjs.service;

import kata.academy.springbootrestjs.model.Role;
import kata.academy.springbootrestjs.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class DBInitializer {

    private final UserService userService;

    public DBInitializer(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostConstruct
    public void initDB() {

        userService.addUser(new User("admin", "admin", (byte) 45, "admin@test.com",
                "admin", Set.of(new Role("ADMIN"))));
        userService.addUser(new User( "user", "user", (byte) 25, "user@test.com",
                "user", Set.of(new Role("USER"))));
    }
}