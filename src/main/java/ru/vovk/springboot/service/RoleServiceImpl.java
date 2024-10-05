package ru.vovk.springboot.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vovk.springboot.model.Role;
import ru.vovk.springboot.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository repository;

    @Override
    public List<Role> getAllRoles() {
        return repository.findAll();
    }

    @Override
    public Set<Role> getRoleByName(List<String> roleName) {
        return new HashSet<>(repository.findByNameIn(roleName));
    }
}
