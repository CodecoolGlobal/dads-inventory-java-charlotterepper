package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.service.ItemService;
import com.codecool.dadsinventory.service.PrincipalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    private final ItemService itemService;
    private final PrincipalService principalService;

    public HomeController(ItemService itemService, PrincipalService principalService) {
        this.itemService = itemService;
        this.principalService = principalService;
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam(name="searchTerm", required = false, defaultValue = "") String searchTerm){
        String title = "Dad's Inventory";
        model.addAttribute("title", title);
        log.info("Searchterm is: " + searchTerm);
        List<Item> items = itemService.getAllBySearchTerm(searchTerm);
        model.addAttribute("items", items);
        model.addAttribute("principal", principalService.getPrincipalName());
        return "index";
    }

    @GetMapping("/home/privacy")
    public String privacy(Model model){
        String title = "Privacy Policy";
        model.addAttribute("title", title);
        model.addAttribute("principal", principalService.getPrincipalName());
        return "privacy";
    }


}
