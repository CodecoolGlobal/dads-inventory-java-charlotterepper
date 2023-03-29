package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.model.Category;
import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.service.ItemService;
import com.codecool.dadsinventory.service.PrincipalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private PrincipalService principalService;

    @Test
    @WithMockUser   // username = user; password = password; roles = {ROLE_USER}
    void testIndex() throws Exception {
        String searchTerm = "ca";
        List<Item> items = List.of(new Item(1L, "Car", new Category(), "", 500_000.0, true));
        when(itemService.getAllBySearchTerm(searchTerm)).thenReturn(items);
        when(principalService.getPrincipalName()).thenReturn("user");

        mvc.perform(get("/").param("searchTerm", searchTerm))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("title", "Dad's Inventory"))
                .andExpect(model().attribute("items", items))
                .andExpect(model().attribute("principal", "user"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser(username="mom", roles = {"MOM"})
    void testPrivacyWithAuthorizedUser() throws Exception {
        when(principalService.getPrincipalName()).thenReturn("mom");

        mvc.perform(get("/home/privacy"))
                .andExpect(status().isOk())
                .andExpect(view().name("privacy"))
                .andExpect(model().attribute("title", "Privacy Policy"))
                .andExpect(model().attribute("principal", "mom"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser(username="dad", roles = {"DAD"})
    void testPrivacyWithUnauthorizedUser() throws Exception {
        when(principalService.getPrincipalName()).thenReturn("dad");

        mvc.perform(get("/home/privacy"))
                .andExpect(status().isFound());
    }
}
