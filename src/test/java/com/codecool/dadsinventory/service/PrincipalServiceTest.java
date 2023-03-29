package com.codecool.dadsinventory.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class PrincipalServiceTest {

    @MockBean
    SecurityContext securityContext;

    @MockBean
    Authentication authentication;

    @Autowired
    PrincipalService principalService;

    @BeforeEach
    void setUp() {
        UserDetails userDetails = new User("testuser", "testpassword", Set.of());
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetPrincipalName() {
        String actual = principalService.getPrincipalName();

        assertEquals("testuser", actual);
    }
}
