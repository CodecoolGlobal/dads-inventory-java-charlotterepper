package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.service.ItemService;
import com.codecool.dadsinventory.service.PrincipalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class ItemController {

    private final ItemService itemService;
    private final PrincipalService principalService;

    public ItemController(ItemService itemService, PrincipalService principalService) {
        this.itemService = itemService;
        this.principalService = principalService;
    }

    @GetMapping("/search")
    public String getAllBySearchTerm(@RequestParam(name = "searchterm") String searchTerm, Model model) {
        List<Item> items = itemService.getAllBySearchTerm(searchTerm);
        String title = "Dad's inventory";
        model.addAttribute("title", title);
        model.addAttribute("items", items);
        return "fragments/lists";
    }

    @GetMapping("/item/details/{id}")
    public String getItemById(@PathVariable("id") Long id, Model model) {
        Item item = itemService.getById(id);
        model.addAttribute("item", item);
        model.addAttribute("principal", principalService.getPrincipalName());
        return "details";
    }
}
