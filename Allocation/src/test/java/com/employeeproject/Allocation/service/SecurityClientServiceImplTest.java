package com.employeeproject.Allocation.service;

import com.employeeproject.Allocation.Exception.APIExceptions;
import com.employeeproject.Allocation.externalClient.SecurityClient;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityClientServiceImplTest {
    @InjectMocks
    SecurityClientServiceImpl securityClientService;
    @Mock
    private SecurityClient securityClient; // Mock the SecurityClient dependency

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testIsTokenValid_ValidToken() {

        String validToken = "valid-token";
        List<String> roles = List.of("ROLE_USER");

        when(securityClient.isTokenValid(validToken, roles)).thenReturn(true); // Mock valid response


        securityClientService.isTokenValid(validToken, roles);
    }

    @Test
    void testIsTokenValid_ExpiredToken() {
        String expiredToken = "expired-token";
        List<String> roles = List.of("ROLE_USER");

        // Simulate FeignException with a message containing "JWT expired"
        FeignException.BadRequest feignException = mock(FeignException.BadRequest.class);
        when(feignException.getMessage()).thenReturn("JWT expired");
        when(securityClient.isTokenValid(expiredToken, roles)).thenThrow(feignException);

        APIExceptions thrown = org.junit.jupiter.api.Assertions.assertThrows(APIExceptions.class, () -> {
            securityClientService.isTokenValid(expiredToken, roles);
        });

        // Check if the correct exception message is thrown for expired token
        org.junit.jupiter.api.Assertions.assertEquals("JWT Token has expired. Please re-authenticate.", thrown.getMessage());
    }

    @Test
    void testIsTokenValid_InvalidToken() {
        String invalidToken = "invalid-token";
        List<String> roles = List.of("ROLE_USER");

        // Simulate FeignException with a message for invalid token
        FeignException.BadRequest feignException = mock(FeignException.BadRequest.class);
        when(feignException.getMessage()).thenReturn("Invalid token");
        when(securityClient.isTokenValid(invalidToken, roles)).thenThrow(feignException);

        APIExceptions thrown = org.junit.jupiter.api.Assertions.assertThrows(APIExceptions.class, () -> {
            securityClientService.isTokenValid(invalidToken, roles);
        });

        // Check if the correct exception message is thrown for invalid token
        org.junit.jupiter.api.Assertions.assertEquals("Invalid token. Please check the token.", thrown.getMessage());
    }

    @Test
    void testIsTokenValid_SecurityServiceUnavailable() {
        String token = "valid-token";
        List<String> roles = List.of("ROLE_USER");
        FeignException feignException = mock(FeignException.class);
        when(feignException.getMessage()).thenReturn("Service unavailable");
        when(securityClient.isTokenValid(token, roles)).thenThrow(feignException);

        APIExceptions thrown = org.junit.jupiter.api.Assertions.assertThrows(APIExceptions.class, () -> {
            securityClientService.isTokenValid(token, roles);
        });

        // Check if the correct exception message is thrown for service unavailability
        org.junit.jupiter.api.Assertions.assertEquals("Security service is unavailable.", thrown.getMessage());
    }
}