package kata.academy.springbootrestjs.service;

import kata.academy.springbootrestjs.model.Role;

import java.util.List;

public interface RoleService {

    void saveRole(Role role);

    Role getRoleById(Long id);

    Role getRoleByType(String type);

    List<Role> getAllRoles();
}
