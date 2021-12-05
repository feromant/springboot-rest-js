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
    private final RoleService roleService;

    public DBInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Transactional
    @PostConstruct
    public void initDB() {

        Role adminRole = new Role("ADMIN");
        Role userRole = new Role("USER");

        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);

        userService.addUser(new User("admin", "admin", (byte) 45, "admin@test.com",
                "admin", Set.of(adminRole, userRole)));
        userService.addUser(new User( "user", "user", (byte) 25, "user@test.com",
                "user", Set.of(userRole)));
    }
}