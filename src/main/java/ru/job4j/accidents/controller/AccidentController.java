package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@ThreadSafe
@RequestMapping("accident")
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

    @GetMapping("/formEditAccident")
    public String edit(Model model, @RequestParam("id") int id) {
        var accident = accidentService.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/accident/error";
        }
        model.addAttribute("accident", accident.get());
        return "accident/editAccident";
    }

    @GetMapping("/error")
    public String error() {
        return "accident/error";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/";
    }
}
