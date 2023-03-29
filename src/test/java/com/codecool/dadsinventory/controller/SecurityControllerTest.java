package com.codecool.dadsinventory.controller;

import com.codecool.dadsinventory.model.Category;
import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.service.ItemService;
import com.codecool.dadsinventory.service.PrincipalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class SecurityControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private PrincipalService principalService;

    @BeforeEach
    void setUp() {
        List<Item> items = List.of(new Item(1L, "Car", new Category(), "", 500_000.0, true));
        when(itemService.getAllBySearchTerm("ca")).thenReturn(items);
        when(principalService.getPrincipalName()).thenReturn("user");
    }

    @Test
    @WithMockUser
    void testGetLoginView() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attribute("title", "Dad's Inventory Login"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser
    void testGetAccessDenied() throws Exception {
        mvc.perform(get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("access-denied"))
                .andExpect(model().attribute("principal", "user"))
                .andExpect(model().hasNoErrors());
    }
}
