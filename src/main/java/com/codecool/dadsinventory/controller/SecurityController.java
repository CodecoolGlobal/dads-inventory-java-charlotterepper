package com.codecool.dadsinventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SecurityController {
    @GetMapping("login")
    public String getLoginView(Model model) {
        String title = "Dad's Inventory Login";
        model.addAttribute("title", title);
        return "login";
    }
}
