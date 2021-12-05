package kata.academy.springbootrestjs.service;

import kata.academy.springbootrestjs.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    User updateUser(User user);
    void deleteUserById(Long id);
}
