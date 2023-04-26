package ru.job4j.accidents.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@ThreadSafe
@Controller
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
