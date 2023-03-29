package com.codecool.dadsinventory.runner;

import com.codecool.dadsinventory.model.Category;
import com.codecool.dadsinventory.model.Item;
import com.codecool.dadsinventory.repository.CategoryRepository;
import com.codecool.dadsinventory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Profile("!test")
public class InitRunner implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        Category smallCat = Category.builder().name("small").build();
        Category mediumCat = Category.builder().name("medium").build();
        Category largeCat = Category.builder().name("large").build();

        smallCat = categoryRepository.saveAndFlush(smallCat);
        mediumCat = categoryRepository.saveAndFlush(mediumCat);
        largeCat = categoryRepository.saveAndFlush(largeCat);

        Item ship = Item.builder().name("Ship").price(5).category(largeCat).comment("").inStock(true).build();
        Item hammer = Item.builder().name("Hammer").price(3).category(mediumCat).comment("").inStock(true).build();
        Item pin = Item.builder().name("Pin").price(2).category(smallCat).comment("").inStock(true).build();
        ship = itemRepository.saveAndFlush(ship);
        hammer = itemRepository.saveAndFlush(hammer);
        pin = itemRepository.saveAndFlush(pin);

        smallCat.setItems(List.of(pin));
        mediumCat.setItems(List.of(hammer));
        largeCat.setItems(List.of(ship));
        categoryRepository.saveAllAndFlush(Arrays.asList(smallCat, mediumCat, largeCat));
    }
}
