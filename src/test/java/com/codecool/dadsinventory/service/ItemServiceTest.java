package com.codecool.dadsinventory.service;

import com.codecool.dadsinventory.model.Category;
import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class ItemServiceTest {
    @MockBean
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    @Test
    void testGetAllBySearchTerm() {
        Item item1 = new Item(1L, "Car", new Category(), "", 500_000.0, true);
        Item item2 = new Item(2L, "Ship", new Category(), "", 100.0, true);
        Item item3 = new Item(3L, "Ashes", new Category(), "", 20.0, true);
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2, item3));
        List<Item> expected = List.of(item2, item3);

        List<Item> actual = itemService.getAllBySearchTerm("Sh");

        assertEquals(expected, actual);
    }

    @Test
    void testGetAllByEmptySearchTerm() {
        Item item1 = new Item(1L, "Car", new Category(), "", 500_000.0, true);
        Item item2 = new Item(2L, "Ship", new Category(), "", 100.0, true);
        when(itemRepository.findAll()).thenReturn(List.of(item1, item2));
        List<Item> expected = List.of(item1, item2);

        List<Item> actual = itemService.getAllBySearchTerm("");

        assertEquals(expected, actual);
    }

    @Test
    void testGetById() {
        Item expected = new Item(1L, "Car", new Category(), "", 500_000.0, true);
        when(itemRepository.findById(1L)).thenReturn(Optional.of(expected));

        Item actual = itemService.getById(1L);

        assertEquals(expected.getName(), actual.getName());
    }
}
