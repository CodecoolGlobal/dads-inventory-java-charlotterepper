package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;


@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final ItemService itemService;

    @Autowired
    public HomeController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public String index(Model model, @RequestParam(name="searchTerm", required = false, defaultValue = "") String searchTerm){
        String title = "Dad's Inventory";
        model.addAttribute("title", title);
        log.info("Searchterm is: " + searchTerm);
        List<Item> items = itemService.getAllBySearchTerm(searchTerm);
        model.addAttribute("title", title);
        model.addAttribute("items", items);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        model.addAttribute("principal", username);
        return "index";
    }

    @GetMapping("/home/privacy")
    public String privacy(Model model){
        String title = "Privacy Policy";
        model.addAttribute("title", title);
        return "privacy";
    }


}
