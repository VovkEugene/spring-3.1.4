package ru.vovk.springboot.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vovk.springboot.model.User;
import ru.vovk.springboot.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @GetMapping
    public String indexPage() {
        return "redirect:/login";
    }

    @GetMapping("/user-page")
    public String getUser(Authentication auth, Model model) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "user-page";
    }
}
