package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.service.PrincipalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class SecurityController {

    private final PrincipalService principalService;

    public SecurityController(PrincipalService principalService) {
        this.principalService = principalService;
    }

    @GetMapping("/login")
    public String getLoginView(Model model) {
        String title = "Dad's Inventory Login";
        model.addAttribute("title", title);
        return "login";
    }

    @GetMapping("/access-denied")
    public String getAccessDenied(Model model) {
        model.addAttribute("principal", principalService.getPrincipalName());
        return "access-denied";
    }
}
