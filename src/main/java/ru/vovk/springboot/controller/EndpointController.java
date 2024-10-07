package ru.vovk.springboot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vovk.springboot.model.Role;
import ru.vovk.springboot.model.User;
import ru.vovk.springboot.service.RoleService;
import ru.vovk.springboot.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class EndpointController {
    private UserService userService;
    private RoleService roleService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/role/{id}")
    public ResponseEntity<Collection<Role>> getRoleById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(user.getRoles());
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/add-user")
    public ResponseEntity<String> addNewUserForm(@RequestParam("username") String username,
                                                 @RequestParam("email") String email,
                                                 @RequestParam("password") String password,
                                                 @RequestParam("roles") List<String> roleNames) {
        Set<Role> roles = roleService.getRoleByName(roleNames);
        userService.saveUser(new User(username, password, email, roles));
        return new ResponseEntity<>("User saved successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/edit-form/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestParam("username") String username,
                                           @RequestParam("email") String email,
                                           @RequestParam("password") String password,
                                           @RequestParam(value = "roles", required = false) List<String> roleNames) {
        User user = userService.getUserById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
        userService.updateUser(id, username, email, password, roleNames);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok("User removed successfully");
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
