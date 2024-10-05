package ru.vovk.springboot.service;

import ru.vovk.springboot.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();

    Set<Role> getRoleByName(List<String> roleName);
}
