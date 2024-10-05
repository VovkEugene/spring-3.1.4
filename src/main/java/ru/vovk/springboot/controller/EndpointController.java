package ru.vovk.springboot.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.vovk.springboot.model.Role;
import ru.vovk.springboot.model.User;
import ru.vovk.springboot.service.RoleService;
import ru.vovk.springboot.service.UserService;

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

    @GetMapping("/user")
    public ResponseEntity<User> getUserById(@RequestParam("id") Long id) {
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
                                           @RequestParam("roles") List<String> roleNames) {
        User user = userService.getUserById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
        userService.updateUser(id, username, email, password, roleNames);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam("id") Long id) {
        if (userService.getUserById(id).isPresent()) {
            userService.deleteUser(id);
            return new ResponseEntity<>("User removed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }
}
