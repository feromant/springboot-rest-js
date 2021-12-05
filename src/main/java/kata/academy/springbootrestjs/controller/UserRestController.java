package kata.academy.springbootrestjs.controller;

import kata.academy.springbootrestjs.model.Role;
import kata.academy.springbootrestjs.model.User;
import kata.academy.springbootrestjs.service.RoleService;
import kata.academy.springbootrestjs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> addUser(@ModelAttribute User user,
                                        @RequestParam(value = "roles") Set<Role> roles) {

        user.getRoles().forEach(role -> roles.add(roleService.getRoleByType(role.getRole())));
        setIdToRoles(roles, user);
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") Set<Role> roles) {
        setIdToRoles(roles, user);
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void setIdToRoles(Set<Role> roles, User user) {
        if (roles != null) {
            for (Role role : roles) {
                if ("ADMIN".equals(role.getRole())) {
                    role.setId(1L);
                }
                else if ("USER".equals(role.getRole())) {
                    role.setId(2L);
                }
                user.addRole(role);
            }
        }
    }
}
