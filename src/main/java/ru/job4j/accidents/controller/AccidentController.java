package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentDataService;
import ru.job4j.accidents.service.AccidentRuleDataService;
import ru.job4j.accidents.service.AccidentTypeDataService;

import javax.servlet.http.HttpServletRequest;

@Controller
@AllArgsConstructor
@ThreadSafe
@RequestMapping("accident")
public class AccidentController {
    private final AccidentDataService service;
    private final AccidentRuleDataService rule;
    private final AccidentTypeDataService type;

    @GetMapping("/addAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", type.findAll());
        model.addAttribute("rules", rule.findAll());
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(rule.findByIdIn(ids));
        service.save(accident);
        return "redirect:/";
    }

    @GetMapping("/formEditAccident")
    public String edit(Model model, @RequestParam("id") int id) {
        var accident = service.findById(id);
        if (accident.isEmpty()) {
            return "redirect:/accident/error";
        }
        model.addAttribute("accident", accident.get());
        model.addAttribute("types", type.findAll());
        model.addAttribute("rules", rule.findAll());
        return "accident/editAccident";
    }

    @GetMapping("/error")
    public String error() {
        return "accident/error";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accident.setRules(rule.findByIdIn(ids));
        if (!service.update(accident)) {
            return "redirect:/accident/updateError";
        }
        return "redirect:/";
    }

    @GetMapping("/updateError")
    public String updateError() {
        return "accident/updateError";
    }
}
