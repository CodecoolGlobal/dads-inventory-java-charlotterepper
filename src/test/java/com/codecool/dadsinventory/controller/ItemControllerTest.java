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

@WebMvcTest
public class ItemControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @MockBean
    private PrincipalService principalService;

    @Test
    @WithMockUser(username = "dad", roles = {"DAD"})
    void testSearch() throws Exception {
        String searchTerm = "ca";
        List<Item> items = List.of(new Item(1L, "Car", new Category(), "", 500_000.0, true));
        when(itemService.getAllBySearchTerm(searchTerm)).thenReturn(items);
        when(principalService.getPrincipalName()).thenReturn("dad");

        mvc.perform(get("/search").param("searchterm", searchTerm))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/lists"))
                .andExpect(model().attribute("title", "Dad's inventory"))
                .andExpect(model().attribute("items", items))
                .andExpect(model().hasNoErrors());
    }
}
