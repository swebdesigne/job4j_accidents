package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@ThreadSafe
@Controller
@AllArgsConstructor
public class IndexController {
    private AccidentService accidentService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("user", "Igor Sivolobov");
        model.addAttribute("accidents", accidentService.findAll());
        return "index";
    }
}
