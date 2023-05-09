package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;
import ru.job4j.accidents.service.RegService;

@Controller
@ThreadSafe
@AllArgsConstructor
public class RegControl {
    private final PasswordEncoder encoder;
    private final UserRepository users;
    private final RegService regService;


    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user) {
        var optUser = regService.save(user);
        if (optUser.isEmpty()) {
            return "redirect:/login?error=true";
        }
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "user/reg";
    }
}
