package com.employeeproject.Allocation.service;

import com.employeeproject.Allocation.Exception.APIExceptions;
import com.employeeproject.Allocation.externalClient.SecurityClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SecurityClientServiceImpl implements SecurityClientService {
    @Autowired
    SecurityClient securityClient;

    @Override
    public void isTokenValid(String authorization, List<String> roles) {
        try {
            if (!securityClient.isTokenValid(authorization, roles)) {
                throw new APIExceptions("Unauthorized");
            }
        } catch (FeignException.BadRequest e) {
            // Handle 400 specifically for expired token or invalid token
            if (e.getMessage().contains("JWT expired")) {
                throw new APIExceptions("JWT Token has expired. Please re-authenticate.");
            } else {
                // Handle other 400 errors (e.g., invalid token)
                throw new APIExceptions("Invalid token. Please check the token.");
            }
        } catch (FeignException e) {
            // Handle other errors (e.g., service unavailable, network issues)
            log.error("Security service error: {}", e.getMessage());
            throw new APIExceptions("Security service is unavailable.");
        }
    }
}
