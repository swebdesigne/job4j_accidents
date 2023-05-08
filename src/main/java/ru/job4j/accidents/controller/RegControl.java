package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthorityRepository;
import ru.job4j.accidents.repository.UserRepository;

@Controller
@ThreadSafe
@AllArgsConstructor
public class RegControl {
    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final AuthorityRepository authorities;


    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (Exception e) {
            return "redirect:/login?error=true";
        }
        return "redirect:/user/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "user/reg";
    }
}
