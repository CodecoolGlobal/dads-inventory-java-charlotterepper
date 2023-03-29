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
public class PrincipalServiceTest {

    @Test
    void testGetPrincipalName() {

    }
}
