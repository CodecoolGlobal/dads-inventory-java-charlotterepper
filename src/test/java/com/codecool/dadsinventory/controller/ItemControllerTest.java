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
import org.springframework.test.context.TestPropertySource;
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
    void testSearchWithAuthorizedUser() throws Exception {
        String searchTerm = "ca";
        String username = "dad";
        List<Item> items = List.of(new Item(1L, "Car", new Category(), "", 500_000.0, true));
        when(itemService.getAllBySearchTerm(searchTerm)).thenReturn(items);
        when(principalService.getPrincipalName()).thenReturn(username);

        mvc.perform(get("/search").param("searchterm", searchTerm))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/lists"))
                .andExpect(model().attribute("title", "Dad's inventory"))
                .andExpect(model().attribute("items", items))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @WithMockUser(username = "mom", roles = {"MOM"})
    void testSearchWithUnauthorizedUser() throws Exception {
        String searchTerm = "ca";
        String username = "mom";
        List<Item> items = List.of(new Item(1L, "Car", new Category(), "", 500_000.0, true));
        when(itemService.getAllBySearchTerm(searchTerm)).thenReturn(items);
        when(principalService.getPrincipalName()).thenReturn(username);

        mvc.perform(get("/search").param("searchterm", searchTerm))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "dad", roles = {"DAD"})
    void testGetItemByIdWithAuthorizedUser() throws Exception {
        Long id = 1L;
        String username = "dad";
        Item item = new Item(1L, "Car", new Category(), "", 500_000.0, true);
        when(itemService.getById(id)).thenReturn(item);
        when(principalService.getPrincipalName()).thenReturn(username);

        mvc.perform(get("/item/details/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("details"))
                .andExpect(model().attribute("item", item))
                .andExpect(model().attribute("principal", username))
                .andExpect(model().hasNoErrors());
    }


}
