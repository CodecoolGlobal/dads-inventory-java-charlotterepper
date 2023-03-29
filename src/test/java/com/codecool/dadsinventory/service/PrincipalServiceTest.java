package com.codecool.dadsinventory.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class PrincipalServiceTest {
    @Autowired
    private PrincipalService principalService;

    @Test
    @WithMockUser   // username = user; password = password; roles = {ROLE_USER}
    void testGetPrincipalName() {
        String actual = principalService.getPrincipalName();

        assertEquals("user", actual);
    }
}
