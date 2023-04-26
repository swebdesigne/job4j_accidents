package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@ThreadSafe
public class AccidentController {
    private final AccidentService accidentService;

    @GetMapping("/addAccident")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/";
    }

    @GetMapping("/formEditAccident/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("id", id);
        return "accident/editAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
