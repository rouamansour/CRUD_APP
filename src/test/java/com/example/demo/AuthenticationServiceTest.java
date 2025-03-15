package com.example.demo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

class AuthenticationServiceTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticate_Success() {
        String email = "roua@example.com";
        String password = "password123";
        UserDetails userDetails = new User(email, "encodedPassword", Collections.emptyList());

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(true);

        boolean result = authenticationService.authenticate(email, password);

        assertTrue(result);
    }

    @Test
    void testAuthenticate_Failure_WrongPassword() {
        String email = "test@example.com";
        String password = "wrongPassword";
        UserDetails userDetails = new User(email, "encodedPassword", Collections.emptyList());

        when(userDetailsService.loadUserByUsername(email)).thenReturn(userDetails);
        when(passwordEncoder.matches(password, "encodedPassword")).thenReturn(false);

        boolean result = authenticationService.authenticate(email, password);

        assertFalse(result);
    }

    @Test
    void testAuthenticate_Failure_UserNotFound() {
        String email = "unknown@example.com";
        String password = "password123";

        when(userDetailsService.loadUserByUsername(email)).thenThrow(new UsernameNotFoundException("User not found"));

        boolean result = authenticationService.authenticate(email, password);

        assertFalse(result);
    }
}
